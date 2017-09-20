package com.mqoo.xop.starter.wechat.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.chanjar.weixin.mp.bean.result.WxMpUser;

/**
 * 微信授权登录支持
 * <p>
 * 
 * @author mingqi.wang
 */
public class WechatLoginSupport {
    final Logger LOG = LoggerFactory.getLogger(getClass());
    
    /**
     * 微信用户处理逻辑
     * 
     * @param user WxMpUser
     * @return userId
     */
    public String  handleWxMpUser(WxMpUser user){
        LOG.info("handle wx user:\n{}",user);
        return user.getOpenId();
    }
}
