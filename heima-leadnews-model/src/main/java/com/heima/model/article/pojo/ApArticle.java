package com.heima.model.article.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("ap_article")
public class ApArticle implements Serializable {
    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private Long id;
    private String title;
    @TableField("author_id")
    private Long authorId;
    @TableField("author_name")
    private String authorName;
    @TableField("channel_id")
    private Long channelId;
    @TableField("channel_name")
    private String channelName;

    private Short layout;
    private Byte flag;
    private String images;
    private String labels;
    private Integer likes;
    private Integer collection;
    private Integer comment;
    private Integer views;
    @TableField("province_id")
    private Integer provinceId;
    @TableField("city_id")
    private Integer cityId;
    @TableField("country_id")
    private Integer countryId;
    @TableField("created_time")
    private Date createdTime;
    @TableField("publish_Time")
    private Date publishTime;
    @TableField("sync_status")
    private Boolean syncStatus;
    private Boolean origin;

    @TableField("static_url")
    private String staticUrl;

}
