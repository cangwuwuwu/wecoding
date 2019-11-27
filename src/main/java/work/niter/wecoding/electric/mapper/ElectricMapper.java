package work.niter.wecoding.electric.mapper;

import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;
import work.niter.wecoding.electric.entity.EleAccount;

/**
 * @Author: Cangwu
 * @Date: 2019/10/6 11:41
 * @Description:
 */
public interface ElectricMapper extends Mapper<EleAccount> {

    @Select("select count(*) from electric_account")
    Integer selectCountFromEleAccount();
}
