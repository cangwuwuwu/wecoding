package work.niter.wecoding.res.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import work.niter.wecoding.exception.RestException;
import work.niter.wecoding.exception.enums.ExceptionEnum;
import work.niter.wecoding.res.entity.ResRate;
import work.niter.wecoding.res.mapper.ResRateMapper;

/**
 * @author Cangwu
 * @date 2019/11/11 23:00
 * @description
 */
@Service
public class ResRateService {

    @Autowired
    private ResRateMapper resRateMapper;

    public void newRate(ResRate rate) {
        int i = resRateMapper.updateIfPresent(rate);
        if (i != 1) {
            throw new RestException(ExceptionEnum.RATE_RES_EXCEPTION);
        }
    }
}
