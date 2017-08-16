package com.mqoo.platform.xop.common.web.api.response;

/**
 * Base api response
 * 
 * @author mingqi.wang
 * @since 2017/7/4
 * @param <T> data type
 */
public class BaseStatusSupportResponse<T> implements StatusSupportResponse {

	private static final long serialVersionUID = 6054195169133080009L;
	protected Integer status = 200;
    protected T data; 

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
	public static BaseStatusSupportResponse<?> buildSuccessResponse() {
		BaseStatusSupportResponse<?> rsp = new BaseStatusSupportResponse<>();
        return rsp;
	}
	
	public static <T> BaseStatusSupportResponse<T> buildSuccessResponse(T data) {
		BaseStatusSupportResponse<T> rsp = new BaseStatusSupportResponse<T>();
		rsp.setData(data);
        return rsp;
	}
}
