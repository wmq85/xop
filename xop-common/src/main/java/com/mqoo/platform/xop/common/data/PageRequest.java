package com.mqoo.platform.xop.common.data;

import java.io.Serializable;

/**
 * 分页请求
 * @author mingqi.wang
 * @since 2017/7/4
 */
public class PageRequest implements Serializable {
	private static final long serialVersionUID = -632470285934872642L;
	private Integer page = 1;
    private Integer rows = 10;
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getRows() {
		return rows;
	}
	public void setRows(Integer rows) {
		this.rows = rows;
	}
}