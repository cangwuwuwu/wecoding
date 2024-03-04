package work.niter.wecoding.admin.spend.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import work.niter.wecoding.alipay.entity.Payment;
import work.niter.wecoding.alipay.mapper.PayMapper;
import work.niter.wecoding.exception.enums.ExceptionEnum;
import work.niter.wecoding.exception.RestException;
import work.niter.wecoding.spend.entity.CompSpend;
import work.niter.wecoding.spend.mapper.SpendMapper;
import work.niter.wecoding.user.entity.CompStudent;
import work.niter.wecoding.user.mapper.CompMapper;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.List;

/**
 * @author xiaozhai
 * @date 2019/11/27 11:03
 * @description
 */
@Service
public class SpendAdminService {

    @Autowired
    private SpendMapper spendMapper;

    @Autowired
    private PayMapper payMapper;

    @Autowired
    private CompMapper compMapper;


    /**
     * 后台管理管理-财务管理 --分页插叙所有财务收支信息信息
     */
    public PageInfo<CompSpend> findSpendInfo(Integer page, Integer size, String search) {
        PageHelper.startPage(page, size);
        List<CompSpend> spends;
        if (StringUtils.isNotBlank(search)) {
            spends = spendMapper.searchSpend(search);
        } else {
            Example example = new Example(CompSpend.class);
            example.setOrderByClause("time DESC");
            spends = spendMapper.selectByExample(example);
        }
        if (CollectionUtils.isEmpty(spends)) {
            throw new RestException(ExceptionEnum.INFO_NOT_FOUND);
        }
        return new PageInfo<>(spends);
    }

    /*查询协会余额*/
    public double findSpendBalance() throws ScriptException {
        List<CompSpend> spends = spendMapper.selectSpend();
//        double totalPayCount = payMapper.selectCount(new Payment());
        double totalPay = 0;
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("JavaScript");
        for (CompSpend s : spends) {
            String expression = totalPay + s.getType() + s.getNumber();
            totalPay = (double) scriptEngine.eval(expression);
        }
        return totalPay;
    }

    /**
     * 后台管理管理-财务管理 --新增修改或财务收支信息
     */
    public void updateOrInsertSpend(CompSpend compSpend) {
        CompSpend spend = new CompSpend();
        spend.setId(compSpend.getId());
        int count = spendMapper.selectCount(spend);
        if (count == 1) {
            spendMapper.updateByPrimaryKey(compSpend);
        } else {
            spendMapper.insertSelective(compSpend);

        }
    }

    /**
     * 后台管理管理-财务管理 --删除财务收支信息
     */
    @Transactional
    public void removeSpend(Integer id) {
        if (id != null) {
            spendMapper.deleteByPrimaryKey(id);
        } else {
            throw new RestException(ExceptionEnum.UNKNOWN_ERROR);
        }
    }

    /**
     * 后台管理管理-财务管理 --插叙所有会费缴纳信息
     *
     * @param page
     * @param size
     * @param search
     * @return
     */
    public PageInfo<Payment> getSpendDuesInfo(Integer page, Integer size, String search) {
        PageHelper.startPage(page, size);
        List<Payment> payments = null;
        if (StringUtils.isNotBlank(search)) {
            payments = payMapper.searchPayment(search);
        } else {
            payments = payMapper.selectPayment();
        }

        if (CollectionUtils.isEmpty(payments)) {
            throw new RestException(ExceptionEnum.INFO_NOT_FOUND);
        }
        return new PageInfo<>(payments);
    }

    public void insertPaymentInfo(Payment payment) {
        Example example = new Example(Payment.class);
        example.createCriteria().andEqualTo("userId", payment.getUserId());
        int count1 = payMapper.selectCountByExample(example);
        if (count1 != 0) {
            throw new RestException(ExceptionEnum.USER_ALSO_EXIST);
        }

        Example stuExample = new Example(CompStudent.class);
        stuExample.createCriteria().andEqualTo("stuId", payment.getUserId());
        int count2 = compMapper.selectCountByExample(stuExample);
        if (count2 == 0) {
            throw new RestException(ExceptionEnum.STU_NOT_IN_INSTITUTE);
        }

        payment.setStatus(20);
        payment.setPaymentType(2);
        payment.setCreateTime(payment.getFinishTime());
        int i = payMapper.insertSelective(payment);
        if (i != 1) {
            throw new RestException(ExceptionEnum.ARGS_NOT_FOUND_ERROR);
        }
    }
}
