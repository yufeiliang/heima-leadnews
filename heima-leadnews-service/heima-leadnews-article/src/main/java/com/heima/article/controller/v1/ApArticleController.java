package com.heima.article.controller.v1;

import com.heima.article.service.ApArticleService;
import com.heima.common.util.ArticleContent;
import com.heima.model.article.dtos.ArticleHomeDto;
import com.heima.model.article.pojo.ApArticle;
import com.heima.model.common.dtos.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/article")
@Api(value = "app端文章-控制器")
public class ApArticleController {
    @Autowired
    private ApArticleService apArticleService;

    @PostMapping(value = "/load")
    @ApiOperation("加载首页")
    public ResponseResult<List<ApArticle>> load(@RequestBody ArticleHomeDto dto) {
        return apArticleService.loadArticleList(ArticleContent.ARTICLE_TYPE_LOAD, dto);
    }

    @PostMapping(value = "/loadmore")
    @ApiOperation("加载更多")
    public ResponseResult<List<ApArticle>> loadMore(@RequestBody ArticleHomeDto dto) {
        return apArticleService.loadArticleList(ArticleContent.ARTICLE_TYPE_MORE, dto);
    }

    @PostMapping(value = "/loadnew")
    @ApiOperation("加载更新")
    public ResponseResult<List<ApArticle>> loadNew(@RequestBody ArticleHomeDto dto) {
        return apArticleService.loadArticleList(ArticleContent.ARTICLE_TYPE_NEW, dto);
    }
}
