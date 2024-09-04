package xyz.content.template.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import xyz.content.template.TemplateApplication;

/**
 * 
 * @author yoke
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User extends TemplateApplication implements Serializable {
    /**
     * 用户ID，非自增
     */
    @TableId
    private String id;

    /**
     * 用户名，必须唯一
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户手机号
     */
    private String phone;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}