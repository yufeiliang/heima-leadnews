package com.heima.model.article.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class ArticleHomeDto {
    Date MaxBehotTime;
    Date MinBehotTime;
    Integer size;
    String tag;
}
