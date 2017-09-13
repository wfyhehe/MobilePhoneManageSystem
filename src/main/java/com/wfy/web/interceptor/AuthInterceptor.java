package com.wfy.web.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wfy.web.common.Const;
import com.wfy.web.common.ResponseCode;
import com.wfy.web.common.ServerResponse;
import com.wfy.web.model.Token;
import com.wfy.web.service.ITokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/11, good luck.
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {
    public static final String LAST_PAGE = "com.wfy.web.lastPage";
    public static List<String> exceptionalUrls = new ArrayList<>();

    static {
        exceptionalUrls.add(Const.SIGN_IN_URL);
        exceptionalUrls.add(Const.SIGN_UP_URL);
        exceptionalUrls.add(Const.VCODE_URL);
        exceptionalUrls.add(Const.CHECK_VCODE_URL);
    }

    private final Logger log = LoggerFactory.getLogger(AuthInterceptor.class);

    @Resource
    private ITokenService iTokenService;

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
        response.setHeader("Access-Control-Allow-Origin", Const.FRONT_END_URL);
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers",
                "Authorization, Origin, X-Requested-With, Content-Type, Accept");
        response.setHeader("Access-Control-Allow-Credentials", "true"); //是否允许浏览器携带用户身份信息（cookie）
        String method = request.getMethod();
        if (method.equals("OPTIONS")) { // 预请求OPTIONS直接放过
            return true;
        }

        String requestUri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String url = requestUri.substring(contextPath.length());
        String header = request.getHeader(Const.AUTHORIZATION);

        log.info("method:" + request.getMethod());
        log.info("requestUri:" + requestUri);
        log.info("contextPath:" + contextPath);
        log.info("url:" + url);
        log.info("header:" + header);
        if (exceptionalUrls.contains(url)) {
            return true; // 例外url（无需token的）可以直接通过
        }
        Token token = Token.parse(header);
        if (!iTokenService.checkToken(token)) {
//            response.setCharacterEncoding("UTF-8");
//            response.setContentType("application/json; charset=utf-8");
//            PrintWriter out = null;

            log.error("Interceptor：错误代码401：未授权，跳转到signIn页面！");
            ServerResponse serverResponse = ServerResponse.createByErrorCodeMessage(
                    ResponseCode.UNAUTHORIZED.getCode(), ResponseCode.UNAUTHORIZED.getDesc());
            ObjectMapper mapper = new ObjectMapper();
            String responseJson = mapper.writeValueAsString(serverResponse);
            log.info(responseJson);
            response.setStatus(ResponseCode.UNAUTHORIZED.getCode());
            return false;
//            response.sendError(401);
//            response.getWriter().append(responseJson);
//            request.getRequestDispatcher("/WEB-INF/jsp/signIn.jsp").forward(request, response);
        }

        return true;
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


}
