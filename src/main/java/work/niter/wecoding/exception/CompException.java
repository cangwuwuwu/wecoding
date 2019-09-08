package work.niter.wecoding.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import work.niter.wecoding.enums.ExceptionEnum;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CompException extends RuntimeException{

    private ExceptionEnum exceptionEnum;
}
