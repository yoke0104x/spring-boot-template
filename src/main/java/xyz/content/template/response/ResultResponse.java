package xyz.content.template.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import xyz.content.template.enums.StatusEnum;

import java.io.Serializable;

/**
 * @program: template
 * @description: yoke
 * @author: yoke
 * @create: 2024-09-04 15:33
 **/
@Getter
@Setter
@Schema(description = "接口响应")
public class ResultResponse<T> implements Serializable {
    private static final long serialVersionUID = -1133637474601003587L;

    /**
     * 接口响应状态码
     */
    @Schema(description = "接口响应状态码")
    private Integer code;

    /**
     * 接口响应信息
     */
    @Schema(description = "接口响应信息")
    private String msg;

    /**
     * 接口响应的数据
     */
    @Schema(description = "接口响应的数据")
    private T data;

    /**
     * 封装成功响应的方法
     * @param data 响应数据
     * @return reponse
     * @param <T> 响应数据类型
     */
    public static <T> ResultResponse<T> success(T data) {

        ResultResponse<T> response = new ResultResponse<>();
        response.setData(data);
        response.setCode(StatusEnum.SUCCESS.code);
        return response;
    }

    /**
     * 封装error的响应
     * @param statusEnum 通用错误信息
     * @return
     * @param <T>
     */
    public static <T> ResultResponse<T> error() {
        return error(StatusEnum.FAIL);
    }

    /**
     * 封装error的响应
     * @param statusEnum error响应的状态值
     * @return
     * @param <T>
     */
    public static <T> ResultResponse<T> error(StatusEnum statusEnum) {
        return error(statusEnum, statusEnum.message);
    }

    /**
     * 封装error的响应  可自定义错误信息
     * @param statusEnum error响应的状态值
     * @return
     * @param <T>
     */
    public static <T> ResultResponse<T> error(StatusEnum statusEnum, String errorMsg) {
        ResultResponse<T> response = new ResultResponse<>();
        response.setCode(statusEnum.code);
        response.setMsg(errorMsg);
        return response;
    }

    /**
     * 封装error的响应  可自定义错误信息和code
     * @param statusEnum error响应的状态值
     * @return
     * @param <T>
     */
    public static <T> ResultResponse<T> error(int code, String errorMsg) {
        ResultResponse<T> response = new ResultResponse<>();
        response.setCode(code);
        response.setMsg(errorMsg);
        return response;
    }

}
