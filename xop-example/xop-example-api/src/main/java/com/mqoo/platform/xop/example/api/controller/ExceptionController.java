package com.mqoo.platform.xop.example.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mqoo.platform.xop.common.exception.authorization.ForbiddenException;
import com.mqoo.platform.xop.common.web.BaseController;

/**
 * 异常示例controller
 * 
 * @author mingqi.wang
 *
 */
@RestController
public class ExceptionController extends BaseController {

    @RequestMapping("/ex")
    @ResponseBody
    String ex(HttpServletRequest request) {
        throw new ForbiddenException("forbiddenException");
    }

}
