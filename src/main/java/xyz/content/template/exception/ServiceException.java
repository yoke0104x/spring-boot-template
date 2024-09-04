package xyz.content.template.exception;

import lombok.Getter;
import xyz.content.template.enums.StatusEnum;

/**
 * @program: 自定义错误信息
 * @description: yoke
 * @author: yoke
 * @create: 2024-09-04 15:39
 **/
@Getter
public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = -3303518302920463234L;

    private final StatusEnum status;

    public ServiceException(StatusEnum status, String message) {
        super(message);
        this.status = status;
    }

    public ServiceException(StatusEnum status) {
        super(status.getMessage());
        this.status = status;
    }
}