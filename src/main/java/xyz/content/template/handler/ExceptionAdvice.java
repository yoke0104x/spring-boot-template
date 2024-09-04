package xyz.content.template.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.UnexpectedTypeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import xyz.content.template.enums.StatusEnum;
import xyz.content.template.exception.ServiceException;
import xyz.content.template.response.ResultResponse;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: template
 * @description: yoke
 * @author: yoke
 * @create: 2024-09-04 15:50
 **/
@Slf4j
@ControllerAdvice
public class ExceptionAdvice {

    /**
     * 处理ServiceException
     * @param serviceException ServiceException
     * @param request 请求参数
     * @return 接口响应
     */
    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public ResultResponse<Void> handleServiceException(ServiceException serviceException, HttpServletRequest request) {
        log.warn("request {} throw ServiceException \n", request, serviceException);
        return ResultResponse.error(serviceException.getStatus(), serviceException.getMessage());
    }

    /**
     * 其他异常拦截
     * @param ex 异常
     * @param request 请求参数
     * @return 接口响应
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultResponse<Void> handleException(Exception ex, HttpServletRequest request) {
        log.error("request {} throw unExpectException \n", request, ex);
        return ResultResponse.error(StatusEnum.SERVICE_ERROR);
    }

    /**
     * 参数非法校验
     * @param ex
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResultResponse<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        try {
            List<ObjectError> errors = ex.getBindingResult().getAllErrors();
            String message = errors.stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(","));
            log.error("param illegal: {}", message);
            return ResultResponse.error(StatusEnum.PARAM_INVALID, message);
        } catch (Exception e) {
            return ResultResponse.error(StatusEnum.SERVICE_ERROR);
        }
    }

    @ExceptionHandler(UnexpectedTypeException.class)
    @ResponseBody
    public ResultResponse<Void> handleUnexpectedTypeException(UnexpectedTypeException ex,
                                                              HttpServletRequest request) {
        log.error("catch UnexpectedTypeException, errorMessage: \n", ex);
        return ResultResponse.error(StatusEnum.PARAM_INVALID, ex.getMessage());
    }


    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ResultResponse<Void> handlerConstraintViolationException(ConstraintViolationException ex, HttpServletRequest request) {
        log.error("request {} throw ConstraintViolationException \n", request, ex);
        return ResultResponse.error(StatusEnum.PARAM_INVALID, ex.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResultResponse<Void> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex,
                                                                      HttpServletRequest request) {
        log.error("request {} throw ucManagerException \n", request, ex);
        return ResultResponse.error(StatusEnum.SERVICE_ERROR);
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class, HttpMediaTypeException.class})
    @ResponseBody
    public ResultResponse<Void> handleMethodNotSupportedException(Exception ex) {
        log.error("HttpRequestMethodNotSupportedException \n", ex);
        return ResultResponse.error(StatusEnum.HTTP_METHOD_NOT_SUPPORT);
    }

}
