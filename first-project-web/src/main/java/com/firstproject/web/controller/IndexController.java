package com.firstproject.web.controller;

import com.firstproject.common.util.Page;
import com.firstproject.domain.bean.Person;
import com.firstproject.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

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
    public String index(HttpServletRequest request) {
        //testService.test();
        String tplPath = request.getSession().getServletContext().getRealPath("/") ;
        String s = this.getClass().getResource("/").getPath() ;
        String ss= this.getClass().getClassLoader().getResource("/").getPath() ;
        HttpServletRequest request1 = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String sss = request1.getSession().getServletContext().getRealPath("/") ;
        return "index" ;
    }

    @RequestMapping("/page")
    public String page(Person person, Page page, Model model) {
        page.setTotalCount(106);
        System.out.println("pageNum:" + page.getPageNum());
        System.out.println("pageSize:" + page.getPageSize());
        System.out.println("totalCount:" + page.getTotalCount());
        System.out.println("pageCount:" + page.getPageCount());
        return "util/page" ;
    }

    @RequestMapping("/jsonPerson")
    @ResponseBody
    public Person jsonPerson() {
        Person p = new Person();
        p.setBirthday(new Date());
        return p;
    }
}
