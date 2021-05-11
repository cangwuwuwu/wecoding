package work.niter.wecoding.user.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;
import work.niter.wecoding.user.entity.Account;

import java.util.List;

/**
 * @author Cangwu
 * @date 2019/7/14 1:29
 * @description
 */
public interface AccountMapper extends Mapper<Account> {

//    @Select("select * from account where stu_username=#{stuUsername}")
//    Account selectByStuUsername(String stuUsername);

    @Insert("insert into account values(#{stuUsername}, #{stuPassword})")
    int addUserPassword(Account account);

    /*查询所有的学号*/
    @Select("select stu_id from account")
    List<String> searchStuId();
}
