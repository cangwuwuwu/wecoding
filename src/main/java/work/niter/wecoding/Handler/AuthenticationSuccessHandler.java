package work.niter.wecoding.Handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

/**
 * @Author: Cangwu
 * @Date: 2019/7/14 1:34
 * @Description:
 */
@Service("authenticationSuccessHandler")
public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    public AuthenticationSuccessHandler() {
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws ServletException, IOException {
        this.logger.info("User: " + request.getParameter("username") + " Login Successfully!");
        this.returnJson(response);
    }

    private void returnJson(HttpServletResponse response) throws IOException {
        response.setStatus(200);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().println("{\"exceptionId\":\"null\",\"messageCode\":\"200\",\"message\": \"Login successfully.\",\"serverTime\": " + System.currentTimeMillis() + "}");
    }
}
