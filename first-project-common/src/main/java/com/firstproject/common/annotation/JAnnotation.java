package com.firstproject.common.annotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.lang.reflect.Method;

/**
 * Description：
 *
 * @author zhouzhongyi1
 * @date 2018/8/3 17:50
 */
@Aspect
@Order(value = Ordered.LOWEST_PRECEDENCE)
public class JAnnotation {
    private String appName;

    public void setAppName(String appName) {
        this.appName = appName;
    }

    @Pointcut("@annotation(com.firstproject.common.annotation.JMonitor)")
    public void JAnnotationPoint() {
    }

    //调用该方法时：配置文件中proxy-target-class="true"必须要设置
    private Method getMethod1(JoinPoint jp) throws Exception {
        MethodSignature msig = (MethodSignature)jp.getSignature();
        Method method = msig.getMethod();
        return method;
    }
    //调用该方法时：配置文件中proxy-target-class可省略或者为false
    private Method getMethod(JoinPoint jp) throws Exception {
        MethodSignature msig = (MethodSignature)jp.getSignature();
        Method realMethod = jp.getTarget().getClass().getDeclaredMethod(msig.getName(),
                msig.getMethod().getParameterTypes()) ;
        return realMethod;
    }
    private boolean isBlank(String value) {
        return null == value || "".equals(value.trim());
    }

    @Around("JAnnotationPoint()")
    public Object execJAnnotation(ProceedingJoinPoint jp) throws Throwable {
        Method method = this.getMethod(jp);
        //建立监控标志
        String monitorTag = null ;
        Object result = null ;
        try {
            JMonitor monitor = method.getAnnotation(JMonitor.class) ;
            if(monitor != null) {
                String jKey = monitor.jKey() ;
                if(!isBlank(jKey)) {
                    String jAppName = monitor.jAppName() ;
                    if(!isBlank(jAppName)) {
                        monitorTag = jAppName + "." + jKey ;
                    } else if (!isBlank(this.appName)) {
                        monitorTag = this.appName + "." + jKey ;
                    }
                    if(!isBlank(monitorTag)) {
                        System.out.println(monitorTag + "：开始监控。");
                    }
                }
            }
            result = jp.proceed() ;
        } catch (Throwable e) {
            if(!isBlank(monitorTag)) {
                System.out.println(monitorTag + "：监控报警。");
            }
            throw e ;
        } finally {
            if(!isBlank(monitorTag)) {
                System.out.println(monitorTag + "：结束监控。");
            }
        }
        return result ;
    }

}
