package com.mqoo.platform.xop.support.exception.handler;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import com.mqoo.platform.xop.common.exception.rest.RestError;

/**
 * Simple {@code RestErrorConverter} implementation that creates a new Map instance based on the specified RestError
 * instance.  Some {@link org.springframework.http.converter.HttpMessageConverter HttpMessageConverter}s (like a JSON
 * converter) can easily automatically convert Maps to response bodies.  The map is populated with the following
 * default name/value pairs:
 *
 * <table>
 *     <tr>
 *         <th>Key (a String)</th>
 *         <th>Value (an Object)</th>
 *         <th>Notes</th>
 *     </tr>
 *     <tr>
 *         <td>status</td>
 *         <td>restError.{@link RestError#getStatus() getStatus()}.{@link org.springframework.http.HttpStatus#value() value()}</td>
 *         <td></td>
 *     </tr>
 *     <tr>
 *         <td>code</td>
 *         <td>restError.{@link RestError#getErrCode() getErrCode()}</td>
 *         <td>Only set if {@code code > 0}</td>
 *     </tr>
 *     <tr>
 *         <td>message</td>
 *         <td>restError.{@link RestError#getErrMsg() getErrMsg()}</td>
 *         <td>Only set if {@code message != null}</td>
 *     </tr>
 *     <tr>
 *         <td>developerMessage</td>
 *         <td>restError.{@link RestError#getDevMsg() getDevMsg()}</td>
 *         <td>Only set if {@code developerMessage != null}</td>
 *     </tr>
 *     <tr>
 *         <td>moreInfoUrl</td>
 *         <td>restError.{@link RestError#getMoreInfoUrl() getMoreInfoUrl()}</td>
 *         <td>Only set if {@code moreInfoUrl != null}</td>
 *     </tr>
 *     <tr>
 *         <td>requestId</td>
 *         <td>restError.{@link RestError#getRequestId()} getRequestId()}</td>
 *         <td>Only set if {@code requestId != null}</td>
 *     </tr>
 * </table>
 * <p/>
 * The map key names are customizable via setter methods (setStatusKey, setMessageKey, etc).
 *
 */
public class MapRestErrorConverter implements RestErrorConverter<Map> {

    private static final String DEFAULT_STATUS_KEY = "status";
    private static final String DEFAULT_CODE_KEY = "errCode";
    private static final String DEFAULT_MESSAGE_KEY = "errMsg";
    private static final String DEFAULT_DEVELOPER_MESSAGE_KEY = "devMsg";
    private static final String DEFAULT_MORE_INFO_URL_KEY = "moreInfoUrl";
    private static final String DEFAULT_REQUEST_ID_KEY = "requestId";

    private String statusKey = DEFAULT_STATUS_KEY;
    private String codeKey = DEFAULT_CODE_KEY;
    private String messageKey = DEFAULT_MESSAGE_KEY;
    private String developerMessageKey = DEFAULT_DEVELOPER_MESSAGE_KEY;
    private String moreInfoUrlKey = DEFAULT_MORE_INFO_URL_KEY;
    private String requestIdKey = DEFAULT_REQUEST_ID_KEY;

    @Override
    public Map convert(RestError re) {
        Map<String, Object> m = createMap();
        HttpStatus status = re.getStatus();
        m.put(getStatusKey(), status.value());

        String code = re.getErrCode();
        if (!StringUtils.isEmpty(code)) {
            m.put(getCodeKey(), code);
        }

        String message = re.getErrMsg();
        if (message != null) {
            m.put(getMessageKey(), message);
        }

        String devMsg = re.getDevMsg();
        if (devMsg != null) {
            m.put(getDeveloperMessageKey(), devMsg);
        }

        String moreInfoUrl = re.getMoreInfoUrl();
        if (moreInfoUrl != null) {
            m.put(getMoreInfoUrlKey(), moreInfoUrl);
        }

        String requestId = re.getRequestId();
        if (null != requestId) {
            m.put(getRequestIdKey(), requestId);
        }

        return m;
    }

    protected Map<String,Object> createMap() {
        return new LinkedHashMap<String, Object>();
    }

    public String getStatusKey() {
        return statusKey;
    }

    public void setStatusKey(String statusKey) {
        this.statusKey = statusKey;
    }

    public String getCodeKey() {
        return codeKey;
    }

    public void setCodeKey(String codeKey) {
        this.codeKey = codeKey;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public void setMessageKey(String messageKey) {
        this.messageKey = messageKey;
    }

    public String getDeveloperMessageKey() {
        return developerMessageKey;
    }

    public String getRequestIdKey() {
        return requestIdKey;
    }

    public void setRequestIdKey(String requestIdKey) {
        this.requestIdKey = requestIdKey;
    }

    public void setDeveloperMessageKey(String developerMessageKey) {
        this.developerMessageKey = developerMessageKey;
    }

    public String getMoreInfoUrlKey() {
        return moreInfoUrlKey;
    }

    public void setMoreInfoUrlKey(String moreInfoUrlKey) {
        this.moreInfoUrlKey = moreInfoUrlKey;
    }
}
