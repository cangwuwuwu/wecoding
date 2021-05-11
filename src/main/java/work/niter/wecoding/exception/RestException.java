package work.niter.wecoding.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import work.niter.wecoding.exception.enums.ExceptionEnum;

/**
 * @author Cangwu
 * @date 2019/9/6 12:51
 * @description
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RestException extends RuntimeException {
    private ExceptionEnum exceptionEnum;
}
