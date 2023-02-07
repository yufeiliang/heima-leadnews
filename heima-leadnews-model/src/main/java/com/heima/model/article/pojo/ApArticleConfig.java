package com.heima.model.article.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("ap_article_config")
public class ApArticleConfig implements Serializable {
    public ApArticleConfig(Long articleId){
        this.articleId = articleId;
        this.isComment = true;
        this.isForward = true;
        this.isDelete = false;
        this.isDown = false;
    }
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;
    /**
     * 文章id
     */
    @TableField("article_id")
    private Long articleId;
    @TableField("is_comment")
    private Boolean isComment;
    @TableField("is_forward")
    private Boolean isForward;
    @TableField("is_down")
    private Boolean isDown;
    @TableField("is_delete")
    private Boolean isDelete;
}
