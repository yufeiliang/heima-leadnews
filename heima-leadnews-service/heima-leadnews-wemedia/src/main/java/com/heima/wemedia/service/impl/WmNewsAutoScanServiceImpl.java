package com.heima.wemedia.service.impl;

import com.alibaba.fastjson.JSON;
import com.heima.apis.article.IArticleClient;
import com.heima.common.aliyun.GreenImageScan;
import com.heima.common.aliyun.GreenTextScan;
import com.heima.file.service.FileStorageService;
import com.heima.model.article.dtos.ArticleDto;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.pojo.WmChannel;
import com.heima.model.wemedia.pojo.WmNews;
import com.heima.wemedia.mapper.WmChannelMapper;
import com.heima.wemedia.mapper.WmNewsMapper;
import com.heima.wemedia.service.WmNewsAutoScanService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Slf4j
@Transactional
public class WmNewsAutoScanServiceImpl implements WmNewsAutoScanService {
    @Autowired
    private WmNewsMapper wmNewsMapper;
    @Autowired
    private GreenTextScan greenTextScan;
    @Autowired
    private GreenImageScan greenImageScan;
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private WmChannelMapper wmChannelMapper;
    @Autowired
    private IArticleClient articleClient;

    /**
     * 自媒体文章审核
     * @param
     */
    @Async
    @Override
    public void autoScanWmNews(Integer id) throws Exception {
        WmNews wmNews = wmNewsMapper.selectById(id);
//        if (wmNews == null) {
//            throw new RuntimeException("WmNewsAutoScanServiceImpl---文章不存在!");
//        }
//        if (wmNews.getStatus().equals(WmNews.Status.SUBMIT.getCode())) {
//            Map<String, String> map = null
        if (StringUtils.isNotBlank(wmNews.getContent())) {
            List<String> list = parseContent(wmNews.getContent(), "text");
            String joined = StringUtils.join(list, wmNews.getTitle(), wmNews.getLabels(), ",");
            Map<String, String> map = greenTextScan.greenTextScan(joined);
            //ElasticSearch->创建索引->创建自定义分词->将数据存入到ES中
            //一篇文章->匹配数据->匹配到-有敏感词

            //敏感词很少->100万->存入数据库->加载到程序内存->内存判断
            //效率很高->DFA算法

            List<String> images = parseContent(wmNews.getContent(), "image");
            String[] split = wmNews.getImages().split(",");
            images.addAll(Arrays.asList(split));
            Set<String> pics = new HashSet<String>();
            pics.addAll(images);
            List<byte[]> arrayList = new ArrayList<byte[]>();
            for (String image : images) {
                byte[] loadFile = fileStorageService.downLoadFile(image);
                arrayList.add(loadFile);
            }
            Map<String, String> imageScan = greenImageScan.greenImageScan(arrayList);
            map.putAll(imageScan);

            int status = 9;
            for (Map.Entry<String, String> entry : imageScan.entrySet()) {
                if (entry.getValue().equals("block")) {
                    status = 2;
                    break;
                } else if (entry.getValue().equals("review")) {
                    status = 3;
                }
            }
            if (status == 9 && wmNews.getPublishTime().getTime() > System.currentTimeMillis()) {
                status = 8;
            }

            wmNews.setStatus((short) status);
            //同步数据到article微服务-200毫秒
            if (status == 9) {
                WmChannel wmChannel = wmChannelMapper.selectById(wmNews.getChannelId());
                ArticleDto dto = new ArticleDto();
                dto.setContent(wmNews.getContent());
                dto.setTitle(wmNews.getTitle());
                dto.setChannelId(Long.valueOf(wmNews.getChannelId()));
                dto.setLayout(wmNews.getType());
                dto.setFlag((byte) 0);
                dto.setImages(wmNews.getImages());
                dto.setLabels(wmNews.getLabels());
                dto.setLikes(0);
                dto.setCollection(0);
                dto.setViews(0);
                dto.setComment(0);
                dto.setCreatedTime(wmNews.getPublishTime());
                dto.setSyncStatus(true);
                dto.setChannelName(wmChannel.getName());
                //识别修改
                if (wmNews.getArticleId() != null) {
                    dto.setId(wmNews.getArticleId());
                }
                //dto.setStaticUrl(???);
                ResponseResult<Long> saveResult = articleClient.save(dto);

                if (wmNews.getArticleId() == null) {
                    wmNews.setArticleId(saveResult.getData());
                }
            }
            //数据同步到数据库
            wmNewsMapper.updateById(wmNews);
        }
    }

    public List<String> parseContent(String content, String match) {
        List<Map> maps = JSON.parseArray(content, Map.class);
        List<String> list = new ArrayList<>();
        for (Map map : maps) {
            String type = map.get("type").toString();
            if (type.equals(match)) {
                list.add(map.get("value").toString());
            }

        }
        return list;
    }
}

