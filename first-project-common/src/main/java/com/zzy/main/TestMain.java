package com.zzy.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.UrlResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;

/**
 * Description：
 *
 * @author zhouzhongyi1
 * DATE： 2018/10/19 14:18
 */
public class TestMain {

    public static void main(String[] args) throws Exception {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring/spring-common.xml");
        JavaMailSender mailSender = (JavaMailSender)ctx.getBean("mailSender");
        MimeMessage mime = mailSender.createMimeMessage();
        MimeMessageHelper helper;

        helper = new MimeMessageHelper(mime,true,"utf-8");
        helper.setFrom("activityme1.com");
        helper.setTo("zhouzhongy1.com");
        helper.setSubject("1发送");
        //需要将附件显示在html中
        //在标签中用cid:xx 标记，使用helper.addInline()方法添加
        helper.setText("文字");
        //helper.addInline("logo", new UrlResource("logo.gif"));
        helper.addAttachment("javaeye.gif", new UrlResource("http://storage.d.local/yao.jd.bucket/f9587fb2-31b7-4f5b-8a16-c5e81c10b9a1.jpg"));
        mailSender.send(mime);
        System.out.println("zz");
//        TestBean b = new Sub();
//        b.ff();
    }

}

class TestBean {
    public void ff() {
        System.out.println("pa");
    }
}

class Sub extends TestBean {
    @Override
    public void ff() {
        super.ff();
        Object o;
        System.out.println("sub");
    }
}
