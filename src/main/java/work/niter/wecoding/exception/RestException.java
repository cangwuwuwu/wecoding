package work.niter.wecoding.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import work.niter.wecoding.enums.ExceptionEnum;

/**
 * @Author: Cangwu
 * @Date: 2019/9/6 12:51
 * @Description:
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RestException extends RuntimeException {
    private ExceptionEnum exceptionEnum;
}
