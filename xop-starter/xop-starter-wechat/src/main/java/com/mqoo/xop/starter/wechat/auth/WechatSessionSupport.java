package com.mqoo.xop.starter.wechat.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mqoo.xop.starter.wechat.auth.web.SessionUtil;

import me.chanjar.weixin.mp.bean.result.WxMpUser;

/**
 * 微信授权登录支持
 * <p>
 * 
 * @author mingqi.wang
 */
public class WechatSessionSupport {
    final Logger LOG = LoggerFactory.getLogger(getClass());

    /**
     * 微信用户登录创建session
     * 
     * @param user WxMpUser
     * @return userId
     */
    public String createSession(WxMpUser user) {
        LOG.info("handle wx user:\n{}", user);
        String openId = user.getOpenId();
        SessionUtil.putUserId(openId);
        return openId;
    }
}
