package xyz.content.template.enums;

import lombok.Getter;

/**
 * @program: 返回的code和message
 * @description: yoke
 * @author: yoke
 * @create: 2024-09-04 15:34
 **/
@Getter
public enum StatusEnum {
    SUCCESS(200 ,"请求处理成功"),
    FAIL(400 ,"请求处理失败"),
    UNAUTHORIZED(401 ,"用户认证失败"),
    FORBIDDEN(403 ,"权限不足"),
    SERVICE_ERROR(500, "服务器去旅行了，请稍后重试"),
    PARAM_INVALID(1000, "无效的参数"),
    HTTP_METHOD_NOT_SUPPORT(1001,"不支持的HTTP方法"),
    ;
    public final Integer code;

    public final String message;

    StatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
