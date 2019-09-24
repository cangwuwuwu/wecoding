package work.niter.wecoding.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import work.niter.wecoding.entity.CompSpend;
import work.niter.wecoding.enums.ExceptionEnum;
import work.niter.wecoding.exception.RestException;
import work.niter.wecoding.mapper.SpendMapper;

import java.util.List;

/**
 * @Author: Cangwu
 * @Date: 2019/9/23 23:05
 * @Description:
 */
@Service
public class SpendService {
    @Autowired
    private SpendMapper spendMapper;

    public void insertSpend(CompSpend compSpend) {
        int i = spendMapper.insert(compSpend);
        if (i == 0) {
            throw new RestException(ExceptionEnum.ARGS_NOT_FOUND_ERROR);
        }
    }

    public PageInfo<CompSpend> findAllSpendByPage(Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<CompSpend> spends = spendMapper.selectAll();
        return new PageInfo<>(spends);
    }
}
