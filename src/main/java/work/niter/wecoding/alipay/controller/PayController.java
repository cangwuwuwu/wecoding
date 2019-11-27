package work.niter.wecoding.alipay.controller;

import com.alipay.api.response.AlipayTradeQueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import work.niter.wecoding.alipay.entity.PreOrder;
import work.niter.wecoding.alipay.service.PayService;
import work.niter.wecoding.exception.entity.ExceptionResult;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author: Cangwu
 * @Date: 2019/10/25 21:46
 * @Description:
 */
@RestController
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private PayService payService;

    @GetMapping("/qrcode")
    public ResponseEntity<String> getQrCode(Integer stuId, String out_trade_no) {
        return ResponseEntity.ok(payService.getQrCodePath(stuId, out_trade_no));
    }

    @PostMapping("/notify")
    public String notify(HttpServletRequest request) {
        Map requestParams = request.getParameterMap();
        String outTradeNo = request.getParameter("out_trade_no");
        String sellerId = request.getParameter("seller_id");
        String tradeStatus = request.getParameter("trade_status");
        String totalAmount = request.getParameter("total_amount");
        return payService.noticeVerified(
                requestParams, outTradeNo, sellerId, tradeStatus, totalAmount);
    }

    @GetMapping("/query")
    public ResponseEntity<ExceptionResult> queryOrderStatus(String outTradeNo) {
        AlipayTradeQueryResponse response = payService.queryPayment(outTradeNo);
        String code = response.getCode();
        return ResponseEntity
                .status("10000".equals(code) ? 200 : 500)
                .body(new ExceptionResult(response));
    }

    @GetMapping("/ispaid")
    public ResponseEntity<Integer> queryStuIsPaid(Integer stuId) {
        return ResponseEntity.ok(payService.findStuIsPaid(stuId));
    }

    @GetMapping("/pcpay")
    public String pcPay(PreOrder preOrder, Integer stuId) {
        return payService.pcPreOrder(preOrder, stuId);
    }

}
