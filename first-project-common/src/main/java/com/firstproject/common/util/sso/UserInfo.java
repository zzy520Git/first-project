package com.firstproject.common.util.sso;

import lombok.Getter;
import lombok.Setter;

/**
 * Description：
 *
 * @author zhouzhongyi1
 * DATE： 2018/10/8 20:33
 */
@Getter
@Setter
public class UserInfo {
    private String username ;
    private String email ;
    private String mobile ;
    private String secret ;

    public UserInfo() {}
    public UserInfo(String username, String email, String mobile, String secret) {
        this.username = username ;
        this.email = email ;
        this.mobile = mobile ;
        this.secret = secret ;
    }

    @Override
    public String toString() {
        return username + ":" + email + ":" + mobile + ":" + secret ;
    }
}
