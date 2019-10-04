package work.niter.wecoding.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import work.niter.wecoding.entity.Account;
import work.niter.wecoding.entity.CompStudent;
import work.niter.wecoding.enums.ExceptionEnum;
import work.niter.wecoding.exception.RestException;
import work.niter.wecoding.mapper.AccountMapper;
import work.niter.wecoding.mapper.CompMapper;

import java.util.List;

@Service
public class CompService {

    @Autowired
    private CompMapper compMapper;
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    /**
     * 存储学生信息
     * @param compStudent
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveStudentMsg(CompStudent compStudent) {
        int i, j;
        try {
            i = compMapper.insert(compStudent);
            Account account = new Account();
            account.setStuId(compStudent.getStuId());
            account.setStuPassword((new BCryptPasswordEncoder())
                    .encode("123456"));
            j = accountMapper.insertSelective(account);
        } catch (DuplicateKeyException e) {
            throw new RestException(ExceptionEnum.USER_ALSO_EXIST);
        }
        if (i != 1 || j != 1){
            throw new RestException(ExceptionEnum.SAVE_COMPSTUDENT_ERROR);
        }
    }

    /**
     * 查询所有学生信息并分页
     * @param page
     * @param size
     * @return
     */
    public PageInfo<CompStudent> findAllStudentMsg(Integer page, Integer size) {
        //分页查询
        PageHelper.startPage(page, size);
        List<CompStudent> students = compMapper.selectAll();
        return new PageInfo<>(students);
    }

    /**
     * 查询所有数据不分页
     * @return
     */
    @Cacheable(value = "stu")
    public List<CompStudent> findAllStuMsg() {
        return compMapper.selectAll();
    }

    /**
     * es名字查询
     */
    public List<String> findStuByName(String name) {
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.queryStringQuery(name))
                .build();
        return elasticsearchTemplate.queryForIds(query);
    }

    /**
     * 根据名字进行模糊查询
     */
    public PageInfo<CompStudent> findMsgByName(String name, Integer page, Integer size) {
        //分页
        PageHelper.startPage(page, size);
        //模糊查询
        Example example = new Example(CompStudent.class);
        Example.Criteria criteria = example.createCriteria();
        //判断name是否为空
        if (StringUtils.isNotBlank(name)) {
            criteria.andLike("stuName", "%" + name + "%");
        }
        List<CompStudent> students = compMapper.selectByExample(example);
        return new PageInfo<>(students);
    }

    /**
     * 根据学号查询
     * @param stuId
     * @return
     */
    public CompStudent getStudentById(String stuId) {
        Example example = new Example(CompStudent.class);
        example.createCriteria()
                .andEqualTo("stuId", stuId);
        return compMapper.selectOneByExample(example);
    }

    /**
     * 根据学号查询条数
     * @param stuId
     * @return
     */
    public int getStudentCountById(String stuId) {
        Example example = new Example(CompStudent.class);
        example.createCriteria()
                .andEqualTo("stuId", stuId);
        return compMapper.selectCountByExample(example);
    }

    /**
     * 修改学生信息
     * @param compStudent
     */
    @Transactional(rollbackFor = Exception.class)
    public void changeMsgById(CompStudent compStudent) {
        int i = compMapper.updateByPrimaryKey(compStudent);
        if (i !=1){
            throw new RestException(ExceptionEnum.UPDATE_MSG_ERROR);
        }
    }

    /**
     * 删除学生信息
     * @param compStudent
     */
    @Transactional(rollbackFor = Exception.class)
    public void removeMsg(CompStudent compStudent) {
        int i = compMapper.delete(compStudent);
        if (i != 1){
            throw new RestException(ExceptionEnum.DELETE_MSG_ERROR);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateStuInfoById(CompStudent student) {
        int i = compMapper.updateByPrimaryKeySelective(student);
        if (i == 0) {
            throw new RestException(ExceptionEnum.UPDATE_MSG_ERROR);
        }
    }
}
