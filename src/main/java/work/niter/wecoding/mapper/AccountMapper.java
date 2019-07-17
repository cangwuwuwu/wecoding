package work.niter.wecoding.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import work.niter.wecoding.entity.Account;

/**
 * @Author: Cangwu
 * @Date: 2019/7/14 1:29
 * @Description:
 */
@Mapper
public interface AccountMapper {
    @Select({"select * from account where stu_username=#{stuUsername}"})
    Account getAccountByUsernameInMapper(String stuUsername);

    @Insert({"insert into account values(#{stuUsername}, #{stuPassword})"})
    void addUserPassword(Account account);
}
