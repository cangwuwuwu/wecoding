package work.niter.wecoding.spend.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import work.niter.wecoding.spend.entity.CompSpend;
import work.niter.wecoding.exception.enums.ExceptionEnum;
import work.niter.wecoding.exception.RestException;
import work.niter.wecoding.spend.mapper.SpendMapper;

import java.util.List;

/**
 * @author Cangwu
 * @date 2019/9/23 23:05
 * @description
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
