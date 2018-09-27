package com.firstproject.web.controller;

import com.firstproject.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Description：
 *
 * @author zhouzhongyi1
 * DATE： 2018/9/27 17:58
 */
@Controller
public class IndexController {
    @Autowired
    private TestService testService ;
    @RequestMapping("/index")
    public String index() {
        testService.test();
        return "index" ;
    }
}
