package com.heima.model.user.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("ap_user")
public class ApUser implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    @TableField("salt")
    private String salt;
    @TableField("name")
    private String name;
    @TableField("password")
    private String password;
    @TableField("phone")
    private String phone;
    @TableField("image")
    private String image;
    @TableField("sex")
    private Boolean sex;
    @TableField("is_identity_authentication")
    private Integer identityAuthentication;
    @TableField("is_certification")
    private Boolean certification;
    @TableField("status")
    private Boolean status;
    @TableField("flag")
    private Short flag;
    @TableField("created_time")
    private Date createdTime;

}
