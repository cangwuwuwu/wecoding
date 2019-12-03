package work.niter.wecoding.admin.spend.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import work.niter.wecoding.alipay.entity.Payment;
import work.niter.wecoding.alipay.mapper.PayMapper;
import work.niter.wecoding.exception.enums.ExceptionEnum;
import work.niter.wecoding.exception.RestException;
import work.niter.wecoding.spend.entity.CompSpend;
import work.niter.wecoding.spend.mapper.SpendMapper;

import java.util.List;

/**
 * @Author xiaozhai
 * @Date 2019/11/27 11:03
 * @Description:
 */
@Service
public class SpendAdminService {

    @Autowired
    private SpendMapper spendMapper;

    @Autowired
    private PayMapper payMapper;

    /**
     * 后台管理管理-财务管理 --分页插叙所有财务收支信息信息
     */
    public PageInfo<CompSpend> findSpendInfo(Integer page, Integer size, String search) {
        PageHelper.startPage(page, size);
        List<CompSpend> spends = null;
        if (StringUtils.isNotBlank(search)){
            spends = spendMapper.searchSpend(search);
        }else {
            spends = spendMapper.selectAll();
        }
        if (CollectionUtils.isEmpty(spends)){
            throw new RestException(ExceptionEnum.INFO_NOT_FOUND);
        }
        PageInfo<CompSpend> pageInfo = new PageInfo<>(spends);
        return pageInfo;
    }

    /**
     * 后台管理管理-财务管理 --新增修改或财务收支信息
     */
    public void updateOrInsertSpend(CompSpend compSpend){
        CompSpend spend = new CompSpend();
        spend.setId(compSpend.getId());
        int count = spendMapper.selectCount(spend);
        if (count == 1){
            spendMapper.updateByPrimaryKey(compSpend);
        }else {
            spendMapper.insertSelective(compSpend);

        }
    }

    /**
     * 后台管理管理-财务管理 --删除财务收支信息
     */
    @Transactional
    public void removeSpend(Integer id) {
        if (id != null){
            spendMapper.deleteByPrimaryKey(id);
        }else {
            throw new RestException(ExceptionEnum.UNKNOWN_ERROR);
        }
    }

    /**
     * 后台管理管理-财务管理 --插叙所有会费缴纳信息
     * @param page
     * @param size
     * @param search
     * @return
     */
    public PageInfo<Payment> getSpendDuesInfo(Integer page, Integer size, String search) {
        PageHelper.startPage(page, size);
        List<Payment> payments = null;
        if (StringUtils.isNotBlank(search)){
            payments = payMapper.searchPayment(search);
        }else {
            payments = payMapper.selectPayment();
        }

        if (CollectionUtils.isEmpty(payments)){
            throw new RestException(ExceptionEnum.INFO_NOT_FOUND);
        }
        PageInfo<Payment> pageInfo = new PageInfo<>(payments);
        return pageInfo;
    }
}
