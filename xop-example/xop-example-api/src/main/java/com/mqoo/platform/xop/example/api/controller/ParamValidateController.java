package com.mqoo.platform.xop.example.api.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mqoo.platform.xop.common.web.api.response.BaseStatusSupportResponse;
import com.mqoo.platform.xop.example.api.controller.Person.CHECKADDR;
import com.mqoo.platform.xop.example.api.controller.Person.CHECKNAME;
import com.mqoo.platform.xop.support.mvc.controller.BaseController;

/**
 * 参数校验示例controller
 * 
 * @author mingqi.wang
 */
@RestController
public class ParamValidateController extends BaseController {

    @RequestMapping("/pv")
    public BaseStatusSupportResponse<Person> pv(@Validated Person person) {
        return BaseStatusSupportResponse.buildSuccessResponse(person);
    }
   
    @RequestMapping("/pv/script")
    public BaseStatusSupportResponse<Person> scriptCheck(@Validated(CHECKNAME.class) Person person) {
        return BaseStatusSupportResponse.buildSuccessResponse(person);
    }
    
    @RequestMapping("/pv/group")
    public BaseStatusSupportResponse<Person> groupCheck(@Validated({CHECKADDR.class,CHECKNAME.class}) Person person) {
        return BaseStatusSupportResponse.buildSuccessResponse(person);
    }
    
}
