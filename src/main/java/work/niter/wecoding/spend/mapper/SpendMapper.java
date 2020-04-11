package work.niter.wecoding.spend.mapper;

import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;
import work.niter.wecoding.spend.entity.CompSpend;

import java.util.List;

/**
 * @Author: Cangwu
 * @Date: 2019/9/23 23:04
 * @Description:
 */
public interface SpendMapper extends Mapper<CompSpend> {

    /**
     * 模糊查询 姓名、描述、时间
     * @param search
     * @return
     */
    @Select("SELECT * FROM comp_spend WHERE CONCAT( NAME,`desc`,time) LIKE CONCAT('%',#{search},'%') order by time desc ")
    public List<CompSpend> searchSpend(String search);

    /*查询会费收支*/
    @Select("select number, type from comp_spend")
    List<CompSpend> selectSpend();

}
