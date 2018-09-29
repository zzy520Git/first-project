package com.firstproject.web.controller;

import com.firstproject.common.util.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Description：
 *
 * @author zhouzhongyi1
 * DATE： 2018/9/27 17:39
 */
@Controller
public class TestController {

    @Autowired
    private HttpClientUtil httpClientUtil ;

    @RequestMapping("/httpclient")
    public void httpclient(HttpServletResponse response) {
        try {
            response.setContentType("text/html;charset=utf-8");
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = null;
            out = response.getWriter();
            out.print(httpClientUtil.getHtml("http://www.baidu.com"));
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
