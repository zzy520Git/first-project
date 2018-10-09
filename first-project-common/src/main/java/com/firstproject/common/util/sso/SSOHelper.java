package com.firstproject.common.util.sso;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Description：
 * 单点登录工具类
 * @author zhouzhongyi1
 * DATE： 2018/10/8 20:36
 */
public class SSOHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(SSOHelper.class) ;

    /**
     * 得到请求的客户端IP地址
     * @param request
     * @return
     */
    public static String getRemoteIP(HttpServletRequest request) {
        String ip = request.getHeader("J-Forwarded-For");
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        } else {
            ip = request.getHeader("X-Forwarded-For");
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
            if (ip != null) {
                int position = ip.indexOf(",");
                if (position > 0) {
                    ip = ip.substring(0, position);
                }
            }
            return ip;
        }
    }

    public static String getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies() ;
        if(cookies != null && cookies.length>0) {
            for(Cookie cookie : cookies) {
                if(name.equals(cookie.getName())) {
                    LOGGER.warn("Cookie name={},value={}", name, cookie.getValue());
                    return cookie.getValue() ;
                }
            }
        }
        return null ;
    }

    public static void setCookie(HttpServletResponse response, String name, String value, int maxAge, String domain) {
        if (name != null) {
            Cookie cookie = new Cookie(name, value);
            cookie.setDomain(domain);
            cookie.setPath("/");
            cookie.setMaxAge(maxAge);
            response.addCookie(cookie);
        }
    }

    public static void logout(HttpServletResponse response, String domain, String cookieName) {
        Cookie cookie = new Cookie(cookieName, "");
        cookie.setDomain(domain);
        cookie.setPath("/");
        /**
         * 设置负数如-1代表cookie放在内存，浏览器关闭则失效
         */
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    public static void logMsg(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.warn("client request URL={}", request.getRequestURL());
        LOGGER.warn("client request URI={}", request.getRequestURI());
        LOGGER.warn("client request ServerName={}", request.getServerName());
        LOGGER.warn("request from client remoteIP={}", SSOHelper.getRemoteIP(request));
    }
}
