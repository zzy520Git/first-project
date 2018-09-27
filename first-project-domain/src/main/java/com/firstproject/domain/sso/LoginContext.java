package com.firstproject.domain.sso;

import lombok.Getter;
import lombok.Setter;

/**
 * Description：
 * 登录信息工具类
 * @author zhouzhongyi1
 * DATE： 2018/8/29 16:11
 */
@Setter
@Getter
public class LoginContext {
    private static final ThreadLocal<LoginContext> holder = new ThreadLocal<>() ;

    private String username ;
    private String email ;
    private String mobile ;

    public static void setLoginContext(LoginContext loginContext) {
        holder.set(loginContext);
    }

    public static LoginContext getLoginContext() {
        return holder.get();
    }

    public static void remove() {
        holder.remove();
    }
}
