package work.niter.wecoding.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import work.niter.wecoding.entity.ExceptionResult;
import work.niter.wecoding.enums.ExceptionEnum;

@ControllerAdvice
public class Advice {

    @ExceptionHandler(CompException.class)
    public ResponseEntity<ExceptionResult> serviceException(CompException compException){
        ExceptionEnum exceptionEnum = compException.getExceptionEnum();
        return ResponseEntity.status(exceptionEnum.getCode()).body(new ExceptionResult(exceptionEnum));
    }
}
