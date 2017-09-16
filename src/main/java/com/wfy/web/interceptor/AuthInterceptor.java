package com.wfy.web.interceptor;

import com.wfy.web.common.Const;
import com.wfy.web.common.ResponseCode;
import com.wfy.web.model.Action;
import com.wfy.web.model.Log;
import com.wfy.web.model.Token;
import com.wfy.web.model.User;
import com.wfy.web.model.enums.LogStatus;
import com.wfy.web.service.IActionService;
import com.wfy.web.service.ILogService;
import com.wfy.web.service.ITokenService;
import com.wfy.web.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/9/11, good luck.
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {
    private static List<String> exceptionalUrls = new ArrayList<>(); // 无需token身份的url

    static {
        exceptionalUrls.add(Const.SIGN_IN_URL); // 登陆
        exceptionalUrls.add(Const.SIGN_UP_URL); // 注册
        exceptionalUrls.add(Const.GET_INFO_URL); // 获取首页信息
        exceptionalUrls.add(Const.VCODE_URL); // 获取验证码图片
        exceptionalUrls.add(Const.CHECK_USERNAME_URL); // 登陆和注册时检查用户名是否存在
        exceptionalUrls.add("/auth/test.do");
    }

    private final Logger log = LoggerFactory.getLogger(AuthInterceptor.class);

    @Resource
    private ITokenService iTokenService;

    @Resource
    private IActionService iActionService;

    @Resource
    private IUserService iUserService;

    @Resource
    private ILogService iLogService;

    /**
     * 在业务处理器处理请求之前被调用
     * 如果返回false
     * 从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链
     * 如果返回true
     * 执行下一个拦截器,直到所有的拦截器都执行完毕
     * 再执行被拦截的Controller
     * 然后进入拦截器链,
     * 从最后一个拦截器往回执行所有的postHandle()
     * 接着再从最后一个拦截器往回执行所有的afterCompletion()
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        log.info("============== AuthInterceptor ================");
        // TODO 跨域设置
//        response.setHeader("Access-Control-Allow-Origin", Const.FRONT_END_URL);
//        response.setHeader("Access-Control-Allow-Methods", "*");
//        response.setHeader("Access-Control-Max-Age", "3600");
//        response.setHeader("Access-Control-Allow-Headers",
//                "Authorization, Origin, X-Requested-With, Content-Type, Accept");
//        response.setHeader("Access-Control-Allow-Credentials", "true"); //是否允许浏览器携带用户身份信息（cookie）
        String method = request.getMethod();

        if (method.equals("OPTIONS")) { // 预请求OPTIONS直接放过
            return true;
        }
        LogStatus status = LogStatus.ACCEPTED;
        String requestUri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String url = requestUri.substring(contextPath.length());
        String header = request.getHeader(Const.AUTHORIZATION);
        String ip = request.getRemoteAddr();
        log.info("method:" + request.getMethod());
        log.info("requestUri:" + requestUri);
        log.info("contextPath:" + contextPath);
        log.info("url:" + url);
        log.info("header:" + header);
        log.info("ip:" + ip);
        Token token = Token.parse(header);
        if (exceptionalUrls.contains(url)) {
            log.info("exceptionalUrl:" + url);
            this.log(url, ip, token, status); // 数据库中加日志
            return true; // 例外url（无需token的）可以直接通过
        }
        if (!iTokenService.checkToken(token)) { // 验证身份 Authentication
            status = LogStatus.UNAUTHENTICATED;
            log.error("Interceptor：错误代码401：未授权，跳转到signIn页面！");
            response.setStatus(ResponseCode.UNAUTHORIZED.getCode());
            this.log(url, ip, token, status); // 数据库中加日志
            return false;
        }
        // 授权 Authorization
        // TODO 把取到的actions存入redis，不然太慢
        boolean isAuthorized;
        if (iUserService.isSuperAdmin(token.getUserId())) {
            log.info("isSuperAdmin:" + token.getUserId());
            isAuthorized = true;
        } else {
            List<String> actionUrls = iActionService.getActionsByUser(new User(token.getUserId()));
            isAuthorized = actionUrls.contains(url);
        }
        status = isAuthorized ? LogStatus.ACCEPTED : LogStatus.UNAUTHORIZED;
        this.log(url, ip, token, status); // 数据库中加日志
        if (!isAuthorized) {
            log.error("Interceptor：错误代码403：权限不足！");
            response.setStatus(ResponseCode.FORBIDDEN.getCode());
        }
        return isAuthorized;
    }

    /**
     * 在业务处理器处理请求执行完成后,生成视图之前执行的动作
     * 可在modelAndView中加入数据，比如当前时间
     */
    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
//        log.info("==============执行顺序: 2、postHandle================");
//        if (modelAndView != null) {  //加入当前时间
//            modelAndView.addObject("var", "测试postHandle");
//        }
    }

    /**
     * 在DispatcherServlet完全处理完请求后被调用,可用于清理资源等
     * <p>
     * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()
     * 一般用于清理资源
     */
    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
//        log.info("==============执行顺序: 3、afterCompletion================");
    }

    private void log(String url, String ip, Token token, LogStatus status) {
        Log log = new Log();
        log.setAction(new Action(url));
        log.setCreateDate(new Date(System.currentTimeMillis()));
        log.setIp(ip);
        String userId = token != null ? token.getUserId() : null;
        if (StringUtils.isNotBlank(userId)) {
            log.setUser(new User(userId));
        }
        log.setStatus(status);
        try {
            iLogService.addLog(log);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
