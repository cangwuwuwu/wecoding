package work.niter.wecoding.electric.service;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import tk.mybatis.mapper.entity.Example;
import work.niter.wecoding.electric.entity.BuildRoom;
import work.niter.wecoding.electric.entity.EleAccount;
import work.niter.wecoding.exception.enums.ExceptionEnum;
import work.niter.wecoding.exception.RestException;
import work.niter.wecoding.electric.mapper.DormMapper;
import work.niter.wecoding.electric.mapper.ElectricMapper;
import work.niter.wecoding.msg.service.MailService;

import java.io.IOException;
import java.util.List;

/**
 * @author Cangwu
 * @date 2019/10/5 11:28
 * @description
 */
@Service
public class ElectricService {
    @Autowired
    private ElectricMapper electricMapper;
    @Autowired
    private DormMapper dormMapper;
    @Autowired
    private MailService mailService;

    @Value("${electric.notice}")
    private Boolean noticeOpen;

    private static final String URL = "http://pay.nit.edu.cn/Pay/CheckRoom";
    private static final HttpMethod METHOD = HttpMethod.POST;
    private static final MultiValueMap<String, String> PARAMS = new LinkedMultiValueMap<>();
    private static final double UPPER = 10D;

    /**
     * 查询楼栋宿舍列表
     *
     * @return
     */
    @Cacheable(value = "ele")
    public List<BuildRoom> findAllBuildRoom() {
        return dormMapper.selectAll();
    }

    /**
     * 直接发送http请求方法
     */
    public String httpRestClient(String url, HttpMethod method, MultiValueMap<String, String> params) throws IOException {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(10 * 1000);
        requestFactory.setReadTimeout(10 * 1000);
        RestTemplate client = new RestTemplate(requestFactory);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
        //  执行HTTP请求
        ResponseEntity<String> response = client.exchange(url, method, requestEntity, String.class);
        return response.getBody();
    }

    /**
     * 开通该功能时查询当前电费
     *
     * @param buildname 楼栋名
     * @param roomname  宿舍名
     * @return 电费
     */
    public String nowCheckMethod(String buildname, String roomname) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("buildname", buildname);
        params.add("roomname", roomname);
        params.add("json", "true");
        String result = null;
        try {
            result = this.httpRestClient(URL, METHOD, params);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String msg = JSON.parseObject(result).getString("msg");
        if (msg == null) {
            return "楼栋或宿舍号错误，请检查";
        }
        String[] number = msg.split(",");
        return number[2];
    }

    /**
     * 每天12点遍历检查电费情况，小于UPPER则邮件提醒
     */
    @Scheduled(cron = "0 0 12 * * *")
    public void checkElectricMethod() {
        if (!noticeOpen) {
            return;
        }
        // 查询表 遍历
        List<EleAccount> list = electricMapper.selectAll();
        list.forEach(account -> {
            PARAMS.add("buildname", account.getStuBuild());
            PARAMS.add("roomname", account.getStuRoom());
            PARAMS.add("json", "true");
            String[] number;
            //发送http请求并返回结果
            try {
                String result = httpRestClient(URL, METHOD, PARAMS);
                String msg = JSON.parseObject(result).getString("msg");
                number = msg.split(",");
                if (Double.parseDouble(number[2]) < UPPER) {
                    System.out.println(account.getStuRoom() + "电量余额还剩：" + number[2]);
                    mailService.sendMailForElectric(
                            account.getStuRoom(), number[2],
                            account.getStuEmail(), account.getStuId());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            PARAMS.clear();
        });
    }

    @Transactional(rollbackFor = Exception.class)
    public void insertBuildRoomAccount(EleAccount account) {
        int i;
        try {
            i = electricMapper.insert(account);
        } catch (DuplicateKeyException e) {
            throw new RestException(ExceptionEnum.USER_ALSO_EXIST);
        }
        if (i != 1) {
            throw new RestException(ExceptionEnum.UNKNOWN_ERROR);
        }
    }

    /**
     * 取消订阅
     *
     * @param stuId
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteBuildRoomAccount(String stuId) {
        int i;
        try {
            Example example = new Example(EleAccount.class);
            example
                    .createCriteria()
                    .andEqualTo("stuId", stuId);
            i = electricMapper.deleteByExample(example);
        } catch (Exception e) {
            throw new RestException(ExceptionEnum.DELETE_MSG_FAILED);
        }
        if (i != 1) {
            throw new RestException(ExceptionEnum.UNKNOWN_ERROR);
        }
    }

    /**
     * 查询开通总人数
     *
     * @return count
     */
    @Transactional(rollbackFor = Exception.class)
    public Integer findEleCount() {
        return electricMapper.selectCountFromEleAccount();
    }
}
