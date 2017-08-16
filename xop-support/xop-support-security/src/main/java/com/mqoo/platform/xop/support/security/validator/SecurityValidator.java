package com.mqoo.platform.xop.support.security.validator;

import com.mqoo.platform.xop.support.security.SecurityValidateContext;

/**
 * Security 验证器接口
 * <p>
 * 通过实现此接口完成验证逻辑
 * 
 * @author mingqi.wang
 * @since 2017/7/4
 */
public interface SecurityValidator {
    /**
     * 验证方法
     * @param securityValidateContext
     * @return
     */
	boolean validate(SecurityValidateContext securityValidateContext);
	
	/**
	 * 排序
	 * <p>
	 * 值小的优先执行
	 * @return
	 */
	int order();
}
