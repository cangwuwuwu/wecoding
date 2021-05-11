package work.niter.wecoding.user.mapper;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;
import work.niter.wecoding.user.entity.CompStudent;

import java.util.List;
public interface CompMapper extends Mapper<CompStudent> {
    /**
     * 模糊查询， 学生姓名、学生电话
     * @param name
     * @return
     */
    @Select("select stu_id,stu_name,stu_phone from comp_stu where CONCAT( stu_name,stu_phone) like  CONCAT('%',#{name},'%')")
    @Results(id = "stuResult", value = {
            @Result(id = true, property = "stuId", column = "stu_id"),
            @Result(property = "stuName", column = "stu_name"),
            @Result(property = "stuPhone", column = "stu_phone")
    })
    List<CompStudent> findInfoWithSearch(String name);

    /**
     * 统计学生所在学院分布情况
     */
//    @Select("select stu_dept, count(*) as count from comp_stu group by stu_dept")
//    List<EchartsInfo> findDeptDistribution();
    @Select("select stu_dept from comp_stu")
    List<String> findDeptInfo();

    /**
     * 统计男生个数
     */
    @Select("SELECT COUNT(*) FROM comp_stu WHERE stu_gender = '男' GROUP BY stu_gender")
    Integer findMaleCount();

    /**
     * 统计总共人数
     */
    @Select("select count(*) from comp_stu")
    Integer findStudentCount();

    /**
     * 根据学号查询学生的名字
     */
    @Select("select stu_name from comp_stu where stu_id = #{userId}")
    String findStuName(String userId);


    /*
    *  模糊查询， 学生姓名、学生电话,并根据账号权限排序
    * */
    @Select("select c.stu_id,c.stu_name,c.stu_phone from comp_stu c join account a on(c.stu_id = a.stu_id) where CONCAT( c.stu_name,c.stu_phone) like  CONCAT('%',#{name},'%') order by a.stu_auth desc")
    @Results(id = "stuResultByAuth", value = {
            @Result(id = true, property = "stuId", column = "stu_id"),
            @Result(property = "stuName", column = "stu_name"),
            @Result(property = "stuPhone", column = "stu_phone")
    })
    List<CompStudent> findInfoWithSearchAndSort(String name);

    /*
     *  模糊查询， 学生姓名、学生电话,并根据账号权限排序
     * */
    @Select("select c.stu_id,c.stu_name,c.stu_phone from comp_stu c join account a on(c.stu_id = a.stu_id)  order by a.stu_auth desc")
    @Results(id = "stuInfoByAuth", value = {
            @Result(id = true, property = "stuId", column = "stu_id"),
            @Result(property = "stuName", column = "stu_name"),
            @Result(property = "stuPhone", column = "stu_phone")
    })
    List<CompStudent> findInfoAndSort();
}
