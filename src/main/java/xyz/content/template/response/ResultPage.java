package xyz.content.template.response;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @program: template
 * @description: yoke
 * @author: yoke
 * @create: 2024-09-05 09:04
 **/
@Data
@Schema(description = "分页响应")
public class ResultPage<T> {
    @Schema(description = "当前页")
    private long current;
    @Schema(description = "每页大小")
    private long size;
    @Schema(description = "总条数")
    private long total;
    @Schema(description = "数据")
    private List<T> list;
    public ResultPage(IPage<T> page) {
        this.current = page.getCurrent();
        this.size = page.getSize();
        this.total = page.getTotal();
        this.list = page.getRecords();
    }

    public static <T> ResultPage<T> of(IPage<T> page) {
        return new ResultPage<T>(page);
    }
}
