package com.heima.model.article.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("ap_article_content")
public class ApArticleContent implements Serializable {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;
    @TableField("article_id")
    private Long articleId;

    private String content;
}
