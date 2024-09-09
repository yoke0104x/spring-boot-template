package xyz.content.template.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import xyz.content.template.TemplateApplication;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author yoke
 * @TableName data
 */
@TableName(value ="data")
@Data
public class DataEntry extends CommEntity implements Serializable {
    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private String userId;

    /**
     * 用户名
     */
    @Schema(description = "用户名")
    private String username;

    /**
     * 号码
     */
    @Schema(description = "号码")
    private String phone;

    /**
     * 状态
     */
    @Schema(description = "状态")
    private String status;

    /**
     * 反馈结果
     */
    @Schema(description = "反馈结果")
    private String results;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}