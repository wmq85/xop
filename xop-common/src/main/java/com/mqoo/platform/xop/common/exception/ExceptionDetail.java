package com.mqoo.platform.xop.common.exception;

/**
 * 异常详情接口
 * 
 * @author mingqi.wang
 * @since 2017/7/6
 */
public interface ExceptionDetail {

    /**
     * get http status code
     * @return
     */
    int getHttpStatusCode();

    /**
     * get errMsg
     * @return
     */
    String getMessage();
    
    /**
     * get errCode
     * @return
     */
    String getErrCode();

    /**
     * get devMsg
     * @return
     */
    String getDevMsg();

    /**
     * get moreInfoUrl
     * @return
     */
    String getMoreInfoUrl();

    /**
     * get isDisableMoreInfoUrl
     * @return
     */
    boolean isDisableMoreInfoUrl();
}
