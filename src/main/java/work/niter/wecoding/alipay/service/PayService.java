package work.niter.wecoding.alipay.service;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import work.niter.wecoding.alipay.entity.Payment;
import work.niter.wecoding.alipay.entity.PreOrder;
import work.niter.wecoding.alipay.mapper.PayMapper;
import work.niter.wecoding.alipay.properties.AlipayProperties;
import work.niter.wecoding.exception.entity.ExceptionResult;
import work.niter.wecoding.exception.enums.ExceptionEnum;
import work.niter.wecoding.exception.RestException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Cangwu
 * @date 2019/10/25 16:27
 * @description https://docs.open.alipay.com/194/106078 扫码支付 跳转支付
 */
@Service
@EnableConfigurationProperties(AlipayProperties.class)
public class PayService {
    @Autowired
    private AlipayProperties properties;
    @Autowired
    private PayMapper payMapper;

//    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Transactional(rollbackFor = Exception.class)
    public String getQrCodePath(Integer stuId, String outTradeNo) {
        // 生成订单创建到数据库
        this.createPayment(outTradeNo, stuId);
        // 生成订单支付二维码
        return this.createQRCodeUrl(outTradeNo);
    }

    private void createPayment(String outTradeNo, Integer stuId) {
        Payment payment = new Payment();
        payment.setOrderNo(outTradeNo);
        payment.setUserId(stuId);
        payment.setStatus(10);
        payment.setPaymentType(1);
        payment.setPayment(Double.parseDouble(properties.getPayNum()));
        int i;
        try {
            i = payMapper.insert(payment);
        } catch (Exception e) {
            throw new RestException(ExceptionEnum.CREATE_PAYMENT_ERROR);
        }
        if (i != 1) {
            throw new RestException(ExceptionEnum.CREATE_PAYMENT_ERROR);
        }
    }

    private String createQRCodeUrl(String outTradeNo) {
        //获得初始化的AlipayClient
        AlipayClient alipayClient = this.getAlipayClient();
        //创建API对应的request类
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        request.setBizContent("{"+
                "    \"out_trade_no\":\"" + outTradeNo + "\","+ //商户订单号
                "    \"total_amount\":\"" + properties.getPayNum() + "\","+ // 会费金额
                "    \"subject\":\" " + properties.getSubject() + "\","+ // 订单标题
                "    \"store_id\":\"AS_001\"," + // 商户门店编号
                "    \"timeout_express\":\"120m\"" + //订单允许的最晚付款时间
                "}");
        request.setNotifyUrl(properties.getNotifyUrl());
        AlipayTradePrecreateResponse response;
        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            throw new RestException(ExceptionEnum.CREATE_QRCODE_ERROR);
        }
        if (!"10000".equals(response.getCode())) {
            throw new RestException(ExceptionEnum.CREATE_QRCODE_ERROR);
        }
        return response.getQrCode();
    }

    public String noticeVerified(
            Map requestParams,
            String outTradeNo,
            String sellerId,
            String tradeStatus,
            String totalAmount) {
        Map<String,String> params = new HashMap<>();
        for (Object o : requestParams.keySet()) {
            String name = (String) o;
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }

        boolean flag = false;
        try {
            flag = AlipaySignature.rsaCheckV1(params,
                    properties.getAlipayPublicKey(),
                    properties.getCharset(),
                    properties.getSignType());
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        if (flag) {
            // 验证单号/收款商家/收款金额/交易状态
            boolean verify = "TRADE_SUCCESS".equals(tradeStatus)
                    || "TRADE_FINISHED".equals(tradeStatus)
                    && totalAmount.equals(properties.getPayNum())
                    && sellerId.equals(properties.getPid());
            if (verify) {
                Payment payment = new Payment();
                payment.setStatus(20);
                payment.setFinishTime(new Date());
                payment.setOrderNo(outTradeNo);
                payMapper.updateStatusAndFinishTime(payment);
                return "success";
            }
        }
        return "failure";
    }

    public AlipayTradeQueryResponse queryPayment(String outTradeNo) {
        AlipayClient alipayClient = this.getAlipayClient();
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        request.setBizContent("{" +
                "\"out_trade_no\":\"" + outTradeNo + "\"," +
                "      \"query_options\":[" +
                "        \"TRADE_SETTLE_INFO\"" +
                "      ]" +
                "  }");
        AlipayTradeQueryResponse response = null;
        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return response;
    }

    public String pcPreOrder(PreOrder preOrder, Integer stuId) {
        this.createPayment(preOrder.getOut_trade_no(), stuId);

        AlipayClient alipayClient = this.getAlipayClient();
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setNotifyUrl(properties.getNotifyUrl());
        preOrder.setProduct_code("FAST_INSTANT_TRADE_PAY");
        alipayRequest.setBizContent(JSON.toJSONString(preOrder));
        String form = "";
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return form;
    }

    private AlipayClient getAlipayClient() {
        return new DefaultAlipayClient(
                properties.getGatewayUrl(),
                properties.getAppid(),
                properties.getAppPrivateKey(),
                properties.getFormate(),
                properties.getCharset(),
                properties.getAlipayPublicKey(),
                properties.getSignType());
    }

    public int findStuIsPaid(Integer stuId) {
        Example example = new Example(Payment.class);
        example
                .createCriteria()
                .andEqualTo("userId", stuId)
                .andEqualTo("status", 20);
        return payMapper.selectCountByExample(example);
    }
}
