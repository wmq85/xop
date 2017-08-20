package com.mqoo.platform.xop.api.gateway.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.AbstractErrorController;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.mqoo.platform.xop.common.exception.BaseException;

/**
 * zuul exception handler
 * 
 * @author mingqi.wang
 * @since 2017/8/17
 */
@RestController
public class ZuulErrorController extends AbstractErrorController {

    @Value("${error.path:/error}")
    private String errorPath;
    @Autowired
    private HandlerExceptionResolver restExceptionHandler;

    public ZuulErrorController(ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }

    @Override
    public String getErrorPath() {
        return errorPath;
    }

    @RequestMapping(value = "${error.path:/error}", produces = "application/json;charset=UTF-8")
    public ModelAndView error(HttpServletRequest request, HttpServletResponse response) {
        Exception ex = (Exception) request.getAttribute("javax.servlet.error.exception");
        Exception cause = (Exception) ex.getCause();
        if (cause != null && cause instanceof BaseException) {
            ex = cause;
        }
        return restExceptionHandler.resolveException(request, response, null, ex);
    }
}
