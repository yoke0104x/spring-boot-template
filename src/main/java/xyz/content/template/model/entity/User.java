package xyz.content.template.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import lombok.Data;
import xyz.content.template.TemplateApplication;

/**
 * 
 * @author yoke
 * @TableName user
 */
@TableName(value ="user")
@Data
@Schema(description = "用户实体类")
public class User extends CommEntity implements Serializable {


    /**
     * 用户名，必须唯一
     */
    @Schema(description = "用户名")
    private String username;

    /**
     * 用户密码
     */
    @Schema(description = "用户密码")
    private String password;

    /**
     * 用户邮箱
     */
    @Schema(description = "用户邮箱")
    private String email;

    /**
     * 用户手机号
     */
    @Schema(description = "用户手机号")
    private String phone;
}