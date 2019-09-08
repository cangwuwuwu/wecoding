package work.niter.wecoding.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import work.niter.wecoding.entity.CompStudent;
import work.niter.wecoding.enums.ExceptionEnum;
import work.niter.wecoding.exception.CompException;
import work.niter.wecoding.mapper.CompMapper;

import java.util.List;

@Service
public class CompService {

    @Autowired
    private CompMapper compMapper;

    //存储学生信息
    @Transactional
    public void saveStudentMsg(CompStudent compStudent) {
        int i = compMapper.insert(compStudent);
        if (i != 1){
            throw new CompException(ExceptionEnum.RESOURCE_UPLOAD_ERROR);
        }
    }

    //查询所有学生信息并分页
    public PageInfo<CompStudent> findAllStudentMsg(Integer page, Integer size) {
        //分页查询
        PageHelper.startPage(page, size);
        List<CompStudent> students = compMapper.selectAll();
        //判断students是否为空
        if (CollectionUtils.isEmpty(students)){
            throw new CompException(ExceptionEnum.NOT_FIND_STUDENT_MSG);
        }
        PageInfo<CompStudent> pageInfo = new PageInfo<>(students);
        return pageInfo;
    }

    //查询所有数据不分页
    public List<CompStudent> findAllStuMsg() {
        List<CompStudent> students = compMapper.selectAll();
        if (CollectionUtils.isEmpty(students)){
            throw new CompException(ExceptionEnum.NOT_FIND_STUDENT_MSG);
        }
        return students;
    }

    //根据名字进行模糊查询
    public PageInfo<CompStudent> findMsgByName(String name, Integer page, Integer size) {
        //分页
        PageHelper.startPage(page, size);
        //模糊查询
        Example example = new Example(CompStudent.class);
        Example.Criteria criteria = example.createCriteria();
        //判断name是否为空
        if (StringUtils.isNotBlank(name)){
            criteria.andLike("stuName", "%" + name + "%");
        }
        List<CompStudent> students = compMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(students)){
            throw new CompException(ExceptionEnum.NOT_FIND_STUDENT_MSG);
        }
        PageInfo<CompStudent> pageInfo = new PageInfo<>(students);
        return pageInfo;
    }

    //修改学生信息
    @Transactional
    public void changeMsgById(CompStudent compStudent) {
        int i = compMapper.updateByPrimaryKeySelective(compStudent);
        if (i !=1){
            throw new CompException(ExceptionEnum.UPDATE_MSG_ERROR);
        }
    }

    //删除学生信息
    @Transactional
    public void removeMsg(CompStudent compStudent) {
        int i = compMapper.delete(compStudent);
        if (i != 1){
            throw new CompException(ExceptionEnum.DELETE_MSG_ERROR);
        }
    }


}
