package com.mqoo.platform.xop.support.mvc.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;

import com.mqoo.platform.xop.common.util.HttpUtil;


/**
 * Controller基类
 * 
 * @author mingqi.wang
 * @since 2017/7/4
 */
public class BaseController {

	private Long[] getIds(final String str, final String separator) {
	    Long[] ids = null;
		if (str != null) {
			String[] strs = str.split(separator);
			ids = new Long[strs.length];
			for (int i = 0, length = strs.length; i < length; i++) {
				ids[i] = Long.valueOf(strs[i]);
			}
		}
		return ids;
	}

	/**
	 * 获取ID列表
	 * <p>
	 * 将1,2,3字符串形式转换为{@code List<Long> }形式
	 * @param ids ids参数
	 * @return List<Long>
	 */
	protected List<Long> getIds(final String ids) {
		return StringUtils.isEmpty(ids) ?  new ArrayList<Long>(0):Arrays.asList(getIds(ids, ",")) ;
	}
	

	/**
	 * 获取IP地址
	 * @param request
	 * @return
	 */
	protected String getIpAddr(HttpServletRequest request) {
		String ip = HttpUtil.getIpAddr(request);
		return ip;
	}
}