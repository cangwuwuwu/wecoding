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
    RESOURCE_UPLOAD_FAILED(500, "资源上传失败"),
    SAVE_COMPSTUDENT_FAILED(500, "学生信息存储失败"),
    ADD_SPEND_FAILED(500, "添加收支失败"),
    DELETE_MSG_FAILED(500, "删除学生信息失败"),
    UPDATE_MSG_FAILED(500, "修改学生信息失败"),
    UPDATE_ACCOUNT_FAILED(500, "修改账户信息失败"),
    DELETE_FAILED_FAILED(500, "删除失败"),
    PASSWORD_MATCH_FAILED(500, "密码验证失败"),
    CODE_MATCH_ERROR(500, "验证码错误"),
    ARGS_NOT_FOUND_ERROR(500, "参数传入错误"),
    UNKNOWN_ERROR(500, "未知错误"),
    USER_ALSO_EXIST(500, "重复录入信息"),
    EMAIL_NULL_EXCEPTION(500, "邮箱不能为空"),
    INFO_NOT_FOUND(500, "您要查询的结果不存在")
    ;
    private int code;
    private String msg;
}
