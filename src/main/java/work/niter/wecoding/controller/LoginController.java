package work.niter.wecoding.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: Cangwu
 * @Date: 2019/7/14 1:22
 * @Description:
 */
@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(@RequestParam(value = "error",required = false) String error,
                        @RequestParam(value = "logout",required = false) String logout,
                        @RequestParam(value = "timeout",required = false) String timeout,
                        Model model) {
        Map<String, Object> map = new HashMap<>(5);
        if (error != null) {
            map.put("msg", "账号或密码错误!");
            map.put("loginClass", "alert alert-danger");
            model.addAllAttributes(map);
        }
        if (logout != null) {
            map.put("msg", "你已成功登出!");
            map.put("loginClass", "alert alert-success");
            model.addAllAttributes(map);
        }
        if (timeout != null) {
            map.put("msg", "登录过期, 请重新登录!");
            map.put("loginClass", "alert alert-warning");
            model.addAllAttributes(map);
        }

        return "login";
    }


    @GetMapping("/login/timeout")
    public void timeout(HttpServletRequest request,
                        HttpServletResponse response) throws IOException {
        response.sendRedirect(request.getContextPath()+"/login?timeout");
    }
}