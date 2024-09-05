package xyz.content.template.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @program: template
 * @description: yoke
 * @author: yoke
 * @create: 2024-09-05 09:24
 **/
@Data
@Schema(description = "分页参数")
public class PageDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "当前页码", example = "1")
    @JsonProperty(defaultValue = "1")
    private int current;

    @Schema(description = "每页记录数", example = "10")
    @JsonProperty(defaultValue = "10")
    private int size;
}
