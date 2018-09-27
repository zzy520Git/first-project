package com.firstproject.domain.resolver;

import com.firstproject.common.exception.BusinessException;
import com.firstproject.common.result.ResponseResult;
import com.firstproject.common.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Description：
 * 统一异常处理器
 * @author zhouzhongyi1
 * @date 2018/8/3 14:22
 */
public class CustomExceptionResolver implements HandlerExceptionResolver {

    private static final Logger logger = LoggerFactory.getLogger(CustomExceptionResolver.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if(ex instanceof BusinessException) {
            logger.error("BusinessException:{}.{}", ((HandlerMethod)handler).getBean(), ((HandlerMethod)handler).getMethod().getName(), ex);
        } else {
            logger.error("System Exception:{}.{}", ((HandlerMethod)handler).getBean(), ((HandlerMethod)handler).getMethod().getName(), ex);
        }
        response.setContentType("text/html;charset=utf-8");
        if(isAjaxRequest(request)) {
            try {
                response.setCharacterEncoding("utf-8");
                response.getWriter().write(JsonUtil.toJSONString(ResponseResult.systemError())) ;
                response.getWriter().close();
            } catch (Exception e) {
                logger.error("CustomExceptionResolver: response.getWriter()", e);
            }
            return null ;
        } else {
            //跳转到错误页面
            ModelAndView modelAndView = new ModelAndView("error/error");
            return modelAndView;
        }
    }

    /**
     * 判断是否为ajax请求
     * @param request 请求
     * @return true 为ajax请求
     */
    private boolean isAjaxRequest(HttpServletRequest request) {
        boolean isAjaxReuest = false;
        if (request.getHeader("X-Requested-With") != null && "XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
            isAjaxReuest = true;
        }
        return isAjaxReuest;
    }
}
