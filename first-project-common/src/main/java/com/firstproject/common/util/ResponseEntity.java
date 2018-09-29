package com.firstproject.common.util;

import lombok.Getter;
import lombok.Setter;
import org.apache.http.Header;
import org.apache.http.HttpStatus;

/**
 * Description：
 *
 * @author zhouzhongyi1
 * DATE： 2018/9/29 17:43
 */
@Setter
@Getter
public class ResponseEntity<T> {
    /* 状态码 */
    private int status= 0;
    /* header */
    private Header[] headers = null;
    /* 响应 */
    private T data;

    public boolean success() {
        return status >= HttpStatus.SC_OK && status < HttpStatus.SC_MULTIPLE_CHOICES;
    }
}
