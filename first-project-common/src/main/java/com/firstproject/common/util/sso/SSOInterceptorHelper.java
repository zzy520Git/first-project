package com.firstproject.common.util.sso;

import com.firstproject.common.util.GuavaCacheUtil;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Description：
 * 单点登录辅助类
 * @author zhouzhongyi1
 * DATE： 2018/10/8 20:37
 */
@Getter
@Setter
public abstract class SSOInterceptorHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(SSOInterceptorHelper.class) ;
    /**
     * 是否打开登陆拦截
     */
    private boolean openInterceptor = true ;
    /**
     * 跳转登录url,本地环境要注意死循环。不能访问https等
     */
    private String loginUrl = "http://www.baidu.com/" ;
    /**
     * 单点登录ssoDomain，一般以.开头
     */
    private String ssoDomain = ".zzy.com" ;
    /**
     * appDomain如果为空，则启用单点登录,利用ssoDomain
     * 该值一般为request.getServerName()
     */
    private String appDomain ;

    public static final String SSO_COOKIE_NAME = "sso.zzy.com";

    private String getSSOLoginUrl(HttpServletRequest request) {
        //TODO 将原url追加到后面
        return this.loginUrl ;
    }

    /**
     * 实际校验token合法性应放在单点登录端，这里仅测试使用
     * @param request
     * @param token
     * @return
     */
    private boolean validateToken(HttpServletRequest request, String token) {
        String ticket = SSOHelper.getCookie(request, SSO_COOKIE_NAME) ;
        if(StringUtils.isNotBlank(ticket)) {
            if(openInterceptor) {
                LOGGER.warn("read from cookie token={}", ticket);
                return true ;
            }
            return token.equals(ticket) ;
        }
        return openInterceptor ;
    }

    private String getToken(HttpServletRequest request) {
        String temp = SSO_COOKIE_NAME + ":" + SSOHelper.getRemoteIP(request) ;
        try {
            return DigestUtils.md5DigestAsHex(temp.getBytes("UTF-8")) ;
        } catch (Exception e) {
            LOGGER.error("validateToken Exception:temp={}", temp);
        }
        return "" ;
    }

    protected LoginContext getLoginContext(HttpServletRequest request, HttpServletResponse response) {
        LoginContext context = null ;
        final String token = this.getToken(request) ;
        if(validateToken(request, token)) {
            UserInfo userInfo = this.getUserInfoFromCache(token) ;
            if(userInfo != null) {
                LOGGER.warn("成功命中本地缓存，用户信息为{}", userInfo.toString());
                context = this.getLoginContextFromUserInfo(userInfo) ;
            } else {
                LOGGER.warn("本地缓存不存在用户信息，尝试读取分布式缓存");
                final String clientIp = SSOHelper.getRemoteIP(request) ;
                String domain = StringUtils.isNotBlank(appDomain) ? appDomain : ssoDomain;
                context = this.getLoginContextFromTicket(token, domain, clientIp) ;
                SSOHelper.setCookie(response, SSO_COOKIE_NAME, context.getSecret(), 2*60, domain);
            }
        }
        return context ;
    }

    private UserInfo getUserInfoFromCache(String token) {
        LOGGER.warn("尝试根据token={}从本地缓存读取用户信息", token);
        return (UserInfo)GuavaCacheUtil.get(token) ;
    }

    private LoginContext getLoginContextFromUserInfo(UserInfo userInfo) {
        LoginContext context = new LoginContext();
        context.setUsername(userInfo.getUsername());
        context.setEmail(userInfo.getEmail());
        context.setMobile(userInfo.getMobile());
        context.setSecret(userInfo.getSecret());
        LoginContext.setLoginContext(context);
        return context ;
    }

    private LoginContext getLoginContextFromTicket(String token, String domain, String clientIp) {
        LoginContext context = null ;
        //TODO 调用UserInfo userInfo = ssoService.verifyToken(token, domain, clientIp);
        UserInfo userInfo = new UserInfo("first-project-user1", domain, clientIp, token) ;

        if(userInfo != null) {
            LOGGER.warn("成功命中分布式缓存，用户信息为{}", userInfo.toString());
            context = this.getLoginContextFromUserInfo(userInfo) ;
            GuavaCacheUtil.put(token, userInfo);
        }
        return context ;
    }

    public void logout(HttpServletResponse response) {
        String domain = StringUtils.isNotBlank(appDomain) ? appDomain : ssoDomain;
        SSOHelper.logout(response, domain, SSO_COOKIE_NAME);
    }

    public void toLoginPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if ("XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"))) {
            response.setStatus(401);
            response.setHeader("Location", this.getSSOLoginUrl(request));
        } else {
            response.sendRedirect(this.getSSOLoginUrl(request));
        }
    }

}
