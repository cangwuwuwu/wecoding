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
    // 返回异常信息
    RESOURCE_UPLOAD_ERROR(500, "资源上传失败"),
    SAVE_COMPSTUDENT_ERROR(500, "学生信息存储失败"),
    UPDATE_MSG_ERROR(500, "修改学生信息失败"),
    DELETE_MSG_ERROR(500, "删除学生信息失败"),
    USER_ALSO_EXIST(500, "该用户信息已存在"),
    INFO_NOT_FOUND(500, "您要查询的结果不存在"),
    ARGS_NOT_FOUND_ERROR(500, "参数传入错误"),
    UNKNOWN_ERROR(500, "未知错误")
    ;
    private int code;
    private String msg;
}
