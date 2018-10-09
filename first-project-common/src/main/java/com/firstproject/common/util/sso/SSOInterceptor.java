package com.firstproject.common.util.sso;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Description：
 * 单点登录拦截器
 * @author zhouzhongyi1
 * DATE： 2018/10/8 20:39
 */
public class SSOInterceptor extends SSOInterceptorHelper implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        SSOHelper.logMsg(request, response);

        LoginContext context = this.getLoginContext(request, response) ;
        if(context != null) {
            return true ;
        }
        this.toLoginPage(request, response);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        LoginContext.remove();
    }
}
