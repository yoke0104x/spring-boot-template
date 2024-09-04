package xyz.content.template.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @program: 通用字段
 * @description: yoke
 * @author: yoke
 * @create: 2024-09-04 16:38
 **/
@Data
public class CommEntity implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID，非自增
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    @Schema(description = "用户ID")
    private String id;


    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
