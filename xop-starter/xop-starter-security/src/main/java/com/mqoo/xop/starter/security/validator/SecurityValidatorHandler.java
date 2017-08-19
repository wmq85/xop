package com.mqoo.xop.starter.security.validator;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import com.google.common.collect.Lists;
import com.mqoo.platform.xop.common.web.BufferedRequestWrapper;
import com.mqoo.xop.starter.security.SecurityProperties;
import com.mqoo.xop.starter.security.SecurityValidateContext;

/**
 * 安全验证处理类
 * <p>
 * 持有排序过的{@link SecurityValidator} 验证器，通过这些验证器完成验证
 * @see SecurityValidator
 * @author mingqi.wang
 * @since 2017/7/4
 */
@Component
public class SecurityValidatorHandler implements InitializingBean {
	Logger LOG=LoggerFactory.getLogger(getClass());
	
	@Autowired(required=false)
	private SecurityValidator[] securityValidators;
	
	private List<SecurityValidator> orderedSecurityValidators;
	
	@Autowired
    private SecurityProperties securityProperties;
	
	/**
	 * Securiy验证处理方法
	 * @param securityValidateContext
	 */
	public void handle(SecurityValidateContext securityValidateContext){
		LOG.debug("security validate start.");
		BufferedRequestWrapper request = securityValidateContext.getRawRequest();
        String requestUri = request.getRequestURI();
		if (urlInGlobalIgnore(requestUri) || urlInIgnore(requestUri)) {
		    LOG.debug("uri:{} in ignore security urls.",requestUri);
		}else{
		    for(SecurityValidator securityValidator :orderedSecurityValidators){
                LOG.debug("security validate handle:{}.",securityValidator.getClass().getSimpleName());
                securityValidator.validate(securityValidateContext);
            }
		}
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		if(securityValidators!=null){
		    orderedSecurityValidators=Lists.newArrayList(securityValidators);
			Collections.sort(orderedSecurityValidators, new Comparator<SecurityValidator>() {
				@Override
				public int compare(SecurityValidator o1, SecurityValidator o2) {
					if(o1.order()>o2.order()){
						return 1;
					}else if(o1.order()<o2.order()){
						return -1;
					}else{
						return 0;
					}
				}
			} );
			LOG.info("security validators loaded:\n");
			for(SecurityValidator securityValidator : orderedSecurityValidators){
			    LOG.info(">>>{}",securityValidator.getClass().getName());
			}
		}else{
		    LOG.warn("no security validator found!");
		}
	}
	
	/**
	 * 判断uri是否在忽略列表中
	 * @param requestUri
	 * @return
	 */
	private boolean urlInIgnore(String requestUri) {
        AntPathMatcher antPathMatcher=new AntPathMatcher();
        List<String> ignoreUrls=securityProperties.getIgnoreSecurityUrls();
        for(String ignoreUrl:ignoreUrls){
            if(antPathMatcher.match(ignoreUrl, requestUri)){
                return true;
            }
        }
        return false;
    }
	/**
     * 判断uri是否在全局忽略列表中
     * @param requestUri
     * @return
     */
	private boolean urlInGlobalIgnore(String requestUri) {
        AntPathMatcher antPathMatcher=new AntPathMatcher();
        List<String> ignoreUrls=securityProperties.getGlobalIgnoreSecurityUrls();
        for(String ignoreUrl:ignoreUrls){
            if(antPathMatcher.match(ignoreUrl, requestUri)){
                return true;
            }
        }
        return false;
    }
}
