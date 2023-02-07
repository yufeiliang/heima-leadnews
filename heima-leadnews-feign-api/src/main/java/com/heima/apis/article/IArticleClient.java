package com.heima.apis.article;

import com.heima.apis.article.fallback.IArticleClientFallback;
import com.heima.model.article.dtos.ArticleDto;
import com.heima.model.common.dtos.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "leadnews-article",path = "/api/v1/article",fallback = IArticleClientFallback.class)
public interface  IArticleClient {
    @PostMapping(value = "/save")
    public ResponseResult save(@RequestBody ArticleDto dto);
}
