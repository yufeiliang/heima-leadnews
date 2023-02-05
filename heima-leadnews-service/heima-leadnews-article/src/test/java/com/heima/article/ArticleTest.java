package com.heima.article;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.heima.article.mapper.ApArticleContentMapper;
import com.heima.article.mapper.ApArticleMapper;
import com.heima.file.service.FileStorageService;
import com.heima.model.article.pojo.ApArticle;
import com.heima.model.article.pojo.ApArticleContent;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest(classes = ArticleApplication.class)
@RunWith(SpringRunner.class)
public class ArticleTest {
    @Autowired
    private ApArticleContentMapper apArticleContentMapper;
    @Autowired
    private Configuration configuration;
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private ApArticleMapper apArticleMapper;


    @Test
    public void createStaticUrlTest() throws IOException, TemplateException {
        Long id = 1302864436297482242L;

//        根据文档id获取文档内容
        ApArticleContent articleContent = apArticleContentMapper.selectOne(Wrappers.<ApArticleContent>lambdaQuery().eq(ApArticleContent::getArticleId, id));
        if (articleContent != null && StringUtils.isNotBlank(articleContent.getContent())) {
//        生成静态页
            StringWriter writer = new StringWriter();
            Template template = configuration.getTemplate("article.ftl");
            Map<String, Object> map = new HashMap<>();
            map.put("content", JSONArray.parseArray(articleContent.getContent()));
            template.process(map, writer);
            ByteArrayInputStream stream = new ByteArrayInputStream(writer.toString().getBytes());

//        上传至minio
            String staticUrl = fileStorageService.uploadHtmlFile("", articleContent.getArticleId() + ".html", stream);
//        保存static_url信息
            ApArticle article = new ApArticle();
            article.setId(id);
            article.setStaticUrl(staticUrl);
            apArticleMapper.updateById(article);

        }
    }
}
