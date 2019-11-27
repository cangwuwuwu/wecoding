package work.niter.wecoding.exception.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import work.niter.wecoding.exception.entity.ExceptionResult;
import work.niter.wecoding.exception.enums.ExceptionEnum;
import work.niter.wecoding.exception.RestException;


/**
 * @Author: Cangwu
 * @Date: 2019/9/6 7:56
 * @Description:
 */
@ControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(RestException.class)
    public ResponseEntity<ExceptionResult> handleException(RestException e) {
        ExceptionEnum exceptionEnum = e.getExceptionEnum();
        return ResponseEntity
                .status(exceptionEnum.getCode())
                .body(new ExceptionResult(exceptionEnum));
    }
}
