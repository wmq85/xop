package com.mqoo.platform.xop.example.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mqoo.platform.xop.common.exception.app.AppAmountLimitException;
import com.mqoo.platform.xop.support.mvc.controller.BaseController;

/**
 * 异常示例controller
 * @author mingqi.wang
 *
 */
@RestController
public class ExceptionController extends BaseController {

    @RequestMapping("/ex")
    @ResponseBody
    String ex(HttpServletRequest request) {
        throw new AppAmountLimitException("AppAmountLimitException");
    }
    
}
