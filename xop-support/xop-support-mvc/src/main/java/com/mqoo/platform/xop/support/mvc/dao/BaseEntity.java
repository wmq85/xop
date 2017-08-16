package com.mqoo.platform.xop.support.mvc.dao;

import java.io.Serializable;

/**
 * 基础实体类
 * <p>
 * 实体类需要继承此类
 * 
 * @author mingqi.wang
 * @since 2017/7/4
 */
public abstract class BaseEntity implements Serializable {
	private static final long serialVersionUID = -5533676942072534213L;
	
    public abstract Serializable getId();
}
