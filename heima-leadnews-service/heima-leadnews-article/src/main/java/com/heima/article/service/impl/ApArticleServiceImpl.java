package com.heima.article.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.article.mapper.ApArticleConfigMapper;
import com.heima.article.mapper.ApArticleContentMapper;
import com.heima.article.mapper.ApArticleMapper;
import com.heima.article.service.ApArticleService;
import com.heima.common.util.ArticleContent;
import com.heima.file.service.FileStorageService;
import com.heima.model.article.dtos.ArticleDto;
import com.heima.model.article.dtos.ArticleHomeDto;
import com.heima.model.article.pojo.ApArticle;
import com.heima.model.article.pojo.ApArticleConfig;
import com.heima.model.article.pojo.ApArticleContent;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

@Service
@Transactional
@Slf4j
public class ApArticleServiceImpl extends ServiceImpl<ApArticleMapper, ApArticle> implements ApArticleService {
    @Autowired
    private ApArticleMapper apArticleMapper;
    @Autowired
    private ApArticleConfigMapper apArticleConfigMapper;

    @Autowired
    private ApArticleContentMapper apArticleContentMapper;

    @Autowired
    private FileStorageService fileStorageService;

    @Override
    public ResponseResult<List<ApArticle>> loadArticleList(Integer type, ArticleHomeDto dto) {
        if (dto.getSize() == null || dto.getSize() == 0 || dto.getSize() > 10) {
            dto.setSize(10);
        }
        if (StringUtils.isEmpty(dto.getTag())) {
            dto.setTag(ArticleContent.DEFAULT_TAG);
        }
        if (ObjectUtils.isEmpty(dto.getMaxBehotTime())) {
            dto.setMaxBehotTime(new Date());
        }
        if (ObjectUtils.isEmpty(dto.getMinBehotTime())) {
            dto.setMinBehotTime(new Date());
        }
        List<ApArticle> apArticles = apArticleMapper.loadArticleList(type, dto);
        return ResponseResult.okResult(apArticles);
    }

    @Override
    public ResponseResult saveArticle(ArticleDto dto) {
//判断dto是否为空
        if (dto == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        ApArticle apArticle = new ApArticle();
        BeanUtils.copyProperties(dto, apArticle);
        //判断id是否存在
        if (dto.getId() == null) {
            //存储信息到ApArticle
            apArticleMapper.insert(apArticle);
            //存储到配置表
            ApArticleConfig apArticleConfig = new ApArticleConfig(dto.getId());
            apArticleConfigMapper.insert(apArticleConfig);
            //存储到内容表
            ApArticleContent apArticleContent = new ApArticleContent();
            apArticleContent.setArticleId(apArticle.getId());
            apArticleContent.setContent(dto.getContent());
            apArticleContentMapper.insert(apArticleContent);
        } else {
            ApArticle article = apArticleMapper.selectOne(Wrappers
                    .<ApArticle>lambdaQuery()
                    .eq(ApArticle::getId, dto.getId()));
            BeanUtils.copyProperties(dto, article);
            apArticleMapper.updateById(article);
            ApArticleContent apArticleContent = apArticleContentMapper.selectOne(Wrappers
                    .<ApArticleContent>lambdaQuery()
                    .eq(ApArticleContent::getArticleId, dto.getId()));
            apArticleContent.setContent(dto.getContent());
            apArticleContentMapper.updateById(apArticleContent);
        }
        return ResponseResult.okResult(apArticle.getId());


    }

}
