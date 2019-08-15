package work.niter.wecoding.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import work.niter.wecoding.entity.Account;

/**
 * @Author: Cangwu
 * @Date: 2019/7/14 1:29
 * @Description:
 */
@Mapper
public interface AccountMapper {

    @Transactional(readOnly = true)
    @Select("select * from account where stu_username=#{stuUsername}")
    Account selectByStuUsername(String stuUsername);

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Insert("insert into account values(#{stuUsername}, #{stuPassword})")
    int addUserPassword(Account account);
}
