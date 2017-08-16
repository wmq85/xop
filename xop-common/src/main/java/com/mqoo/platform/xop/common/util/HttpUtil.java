package com.mqoo.platform.xop.common.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;

/**
 * HttpUtil
 * @author mingqi.wang
 *
 */
public class HttpUtil {
	/**
	 * 获取IP地址
	 * @param request
	 * @return ip
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Real-IP");
		if (!StringUtils.isEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}
		ip = request.getHeader("X-Forwarded-For");
		if (!StringUtils.isEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个IP值，第一个为真实IP。
			int index = ip.indexOf(',');
			if (index != -1) {
				return ip.substring(0, index);
			}
			else {
				return ip;
			}
		}
		else {
			return request.getRemoteAddr();
		}
	}
}
