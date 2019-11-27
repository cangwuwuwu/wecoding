package work.niter.wecoding.user.mapper;

import org.apache.ibatis.annotations.Insert;
import tk.mybatis.mapper.common.Mapper;
import work.niter.wecoding.user.entity.Account;

/**
 * @Author: Cangwu
 * @Date: 2019/7/14 1:29
 * @Description:
 */
public interface AccountMapper extends Mapper<Account> {

//    @Select("select * from account where stu_username=#{stuUsername}")
//    Account selectByStuUsername(String stuUsername);

    @Insert("insert into account values(#{stuUsername}, #{stuPassword})")
    int addUserPassword(Account account);
}
