package com.heima.article.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.article.mapper.ApArticleMapper;
import com.heima.article.service.ApArticleService;
import com.heima.common.util.ArticleContent;
import com.heima.file.service.FileStorageService;
import com.heima.model.article.dtos.ArticleHomeDto;
import com.heima.model.article.pojo.ApArticle;
import com.heima.model.common.dtos.ResponseResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

@Service
public class ApArticleServiceImpl extends ServiceImpl<ApArticleMapper, ApArticle> implements ApArticleService {
    @Autowired
    private ApArticleMapper apArticleMapper;

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

}
