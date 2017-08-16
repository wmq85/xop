package com.mqoo.platform.xop.example.api.controller;

import javax.validation.constraints.Min;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.ScriptAssert;

import com.mqoo.platform.xop.common.exception.validation.ValueInvalidException;
import com.mqoo.platform.xop.example.api.controller.Person.CHECKNAME;

@ScriptAssert(lang = "javascript",
        script = "com.mqoo.platform.xop.example.api.controller.Person.checkName(_this.name)",
        groups = CHECKNAME.class)
//@ScriptAssert(lang = "javascript", script = "_this.passVerify.equals(_this.pass)")
public class Person {
    private String name;
    @Min(value = 3, message = "年龄不能小于3")
    private Integer age;
    @NotBlank(groups={CHECKADDR.class})
    private String addr;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public interface CHECKNAME {
    };
    public interface CHECKADDR {
    };

    public static boolean checkName(String name) {
        if(!StringUtils.startsWith(name, "n")){
            ValueInvalidException ex= new ValueInvalidException("no starts with 'n'");
            throw ex;
        }
        return true;
    }
}
