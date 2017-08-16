package com.mqoo.platform.xop.common.web.api.response;

/**
 * status support api 响应接口
 * 
 * @author mingqi.wang
 * @since 2017/7/4
 */
public interface StatusSupportResponse extends Response {

    /**
     * Get http status code
     *
     * @return http status code
     */
    Integer getStatus();

    /**
     * Set http status code
     *
     * @param status the http status code to set
     */
    void setStatus(Integer status);

}
