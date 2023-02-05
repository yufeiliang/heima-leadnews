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
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;
    @TableField("is_comment")
    private Boolean isComment;
    @TableField("is_forward")
    private Boolean isForward;
    @TableField("is_down")
    private Boolean isDown;
    @TableField("is_delete")
    private Boolean isDelete;
}
