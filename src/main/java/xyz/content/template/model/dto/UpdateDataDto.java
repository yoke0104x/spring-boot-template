package xyz.content.template.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import xyz.content.template.model.entity.CommEntity;

/**
 * @program: template
 * @description: yoke
 * @author: yoke
 * @create: 2024-09-06 14:54
 **/
@Data
@Schema(description = "更新数据")
public class UpdateDataDto extends CommEntity {
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
}
