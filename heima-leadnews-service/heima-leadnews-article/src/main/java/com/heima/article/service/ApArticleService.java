package com.heima.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.article.dtos.ArticleDto;
import com.heima.model.article.dtos.ArticleHomeDto;
import com.heima.model.article.pojo.ApArticle;
import com.heima.model.common.dtos.ResponseResult;

import java.util.List;

public interface ApArticleService extends IService<ApArticle> {
    ResponseResult<List<ApArticle>> loadArticleList(Integer type,ArticleHomeDto dto);

    ResponseResult saveArticle(ArticleDto dto);
}
