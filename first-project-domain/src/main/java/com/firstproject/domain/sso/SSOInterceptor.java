package com.firstproject.domain.sso;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Description：
 * 单点登录拦截器
 * @author zhouzhongyi1
 * DATE： 2018/8/28 20:13
 */
@Setter
@Getter
public class SSOInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(SSOInterceptor.class) ;

    private SSOHelper ssoHelper ;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        this.log(request, response);
        if(isExclude(request)) {
            return true ;
        } else {
            LoginContext context = this.getLoginContext(request);
            if (context == null) {
                ssoHelper.toLoginPage(request, response);
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        LoginContext.remove();
    }

    private boolean isExclude(HttpServletRequest request) {
        String uri = request.getRequestURI() ;
        logger.warn("client request URI={}", uri);
        if(ssoHelper.getExcludePathCache()!=null && !ssoHelper.getExcludePathCache().isEmpty()) {
            for(String path : ssoHelper.getExcludePathCache()) {
                if(uri.startsWith(path)) {
                    logger.warn("client request URI startsWith path={}", path);
                    return true ;
                }
            }
        }
        return false ;
    }

    private LoginContext getLoginContext(HttpServletRequest request) {
        if(ssoHelper.validateToken(request)) {
            LoginContext context = ssoHelper.getLoginContextFromCache(request) ;
            if(context==null) {
                context = ssoHelper.getLoginContextFromRedis(request) ;
            }
            return context ;
        } else {
            return null ;
        }
    }

    private void log(HttpServletRequest request, HttpServletResponse response) {
        logger.warn("client request URL={}", request.getRequestURL());
        logger.warn("client request ServerName={}", request.getServerName());
    }
}
