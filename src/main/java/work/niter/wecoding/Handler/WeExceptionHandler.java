package work.niter.wecoding.Handler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @Author: Cangwu
 * @Date: 2019/7/14 1:38
 * @Description:
 */

@ControllerAdvice
public class WeExceptionHandler {

    @ExceptionHandler({SQLIntegrityConstraintViolationException.class})
    public Map<String, Object> SQLIntegrityConstraintViolationExceptionHandle(
            SQLIntegrityConstraintViolationException e
    ) {
        Map<String, Object> result = new HashMap();
        result.put("message", "数据库操作错误");
        return result;
    }
}
