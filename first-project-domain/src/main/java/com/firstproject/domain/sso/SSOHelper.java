package com.firstproject.domain.sso;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;

import javax.annotation.PostConstruct;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Description：
 *
 * @author zhouzhongyi1
 * DATE： 2018/8/28 19:58
 */
@Getter
@Setter
public class SSOHelper {
    private static final Logger logger = LoggerFactory.getLogger(SSOHelper.class) ;

    private String tokenKey = "sso.zzy.com" ;
    private String loginUrl = "http://sso.zzy.com/sso/login" ;
    private String domain = ".zzy.com" ;
    private String excludePath = "" ;
    private int cacheSize = 10;
    /**
     * 是否打开单点登录
     */
    private boolean ssoEnabled = true ;

    //不支持依赖注入
    private List<String> excludePathCache;
    private Map<String, String> loginCache ;

    /**
     * 得到请求的客户端IP地址
     * @param request
     * @return
     */
    public String getRemoteIP(HttpServletRequest request) {
        String ip = request.getHeader("J-Forwarded-For");
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            logger.warn("request from client remoteIP={}", ip);
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
            logger.warn("request from client remoteIP={}", ip);
            return ip;
        }
    }

    public void logout(HttpServletResponse response) {
        logger.warn("client is logout");
        Cookie cookie = new Cookie(tokenKey,null);
        if (domain != null && !"".equals(domain.trim())) {
            cookie.setDomain(domain);
        }
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    public String getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies() ;
        if(cookies != null && cookies.length>0) {
            for(Cookie cookie : cookies) {
                if(name.equals(cookie.getName())) {
                    logger.warn("Cookie name={},value={}", name, cookie.getValue());
                    return cookie.getValue() ;
                }
            }
        }
        return null ;
    }

    public void toLoginPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if ("XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"))) {
            response.setStatus(401);
            response.setHeader("Location", this.getSSOLoginUrl(request));
        } else {
            String ssoLoginUrl = this.getSSOLoginUrl(request) ;
            logger.warn("to ssoLoginUrl={}", ssoLoginUrl);
            response.sendRedirect(ssoLoginUrl);
        }
    }

    public void setCookie(HttpServletResponse response, String name, String value, int maxAge, String domain) {
        if (name != null) {
            Cookie cookie = new Cookie(name, value);
            if (StringUtils.isNotBlank(domain)) {
                cookie.setDomain(domain);
            } else if(StringUtils.isNotBlank(this.domain)) {
                cookie.setDomain(this.domain);
            }
            cookie.setPath("/");
            cookie.setMaxAge(maxAge);
            response.addCookie(cookie);
        }
    }

    public String getTokenValue(HttpServletRequest request) {
        final String ticket = this.tokenKey + ":" + this.getRemoteIP(request) ;
        String tokenValue = null ;
        try {
            tokenValue = DigestUtils.md5DigestAsHex(ticket.getBytes("UTF-8")) ;
        } catch (UnsupportedEncodingException e) {
            logger.error("getTokenValue UnsupportedEncodingException:ticket={},tokenValue={}", ticket, tokenValue);
        }
        logger.warn("getTokenValue:ticket={},tokenValue={}", ticket, tokenValue);
        return tokenValue ;
    }

    public boolean validateToken(HttpServletRequest request) {
        String tokenValue = this.getTokenValue(request) ;
        //TODO 开关
        if(!ssoEnabled) {
            return true ;
        }
        if(StringUtils.isNotBlank(tokenValue)) {
            return tokenValue.equals(this.getCookie(request, this.tokenKey)) ;
        }
        return false ;
    }

    private String getUserFromCache(String token) {
        return this.loginCache.get(token) ;
    }

    public LoginContext getLoginContextFromCache(HttpServletRequest request) {
        LoginContext context = new LoginContext();
        String username = this.getUserFromCache(this.getTokenValue(request)) ;
        if(username == null) {
            logger.warn("本地缓存找不到登陆信息");
            return null ;
        }
        //TODO
        //如果username已经过期，返回null，这里是永不过期
        context.setUsername(username);
        LoginContext.setLoginContext(context);
        return context ;
    }

    public LoginContext getLoginContextFromRedis(HttpServletRequest request) {
        //TODO
        /**
         * 单点登录处理，调用RPC服务查Redis
         */
        logger.warn("单点登录处理，调用RPC服务查Redis,并加入本地缓存");
        this.loginCache.put(this.getTokenValue(request), "hello world") ;
        return this.getLoginContextFromCache(request) ;
    }

    private String getSSOLoginUrl(HttpServletRequest request) {
        //TODO
        return this.loginUrl ;
    }

    @PostConstruct
    private void init() {
        logger.warn("init excludePath={}", this.excludePath);
        if(StringUtils.isNotBlank(this.excludePath)) {
            this.excludePathCache = new ArrayList<>();
            String[] path = excludePath.split(",");
            for(String p : path) {
                excludePathCache.add(p.trim()) ;
            }
        }
        this.loginCache = Collections.synchronizedMap(new LinkedHashMap<String, String>(this.cacheSize, 0.75F, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry eldest) {
                return this.size() > SSOHelper.this.cacheSize;
            }
        });
        logger.warn("init loginCache finished");
    }

}
