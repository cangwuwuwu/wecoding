package work.niter.wecoding.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Author: Cangwu
 * @Date: 2019/9/6 8:05
 * @Description: 异常枚举类
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ExceptionEnum {
    // 返回资源上传失败信息
    RESOURCE_UPLOAD_ERROR(400, "资源上传失败！"),
    SAVE_COMPSTUDENT_ERROR(500, "学生信息存储失败"),
    NOT_FIND_STUDENT_MSG(404, "没有查询到学生信息"),
    ;
    private int code;
    private String msg;
}
