package com.mqoo.xop.starter.data;

import java.util.List;

import org.springframework.data.domain.Page;

import com.mqoo.platform.xop.common.data.PageInfo;

public interface Repository {
    /**
     * 将spring data Page对象转换为PageInfo
     * 
     * @param page spring data Page分页对象
     * @return PageInfo<T> 分页对象
     */
    static public <T> PageInfo<T> pageConvert(Page<T> page) {
        int pageNum = page.getNumber() + 1;
        int pageSize = page.getSize();
        List<T> content = page.getContent();
        long total = page.getTotalElements();
        PageInfo<T> pageInfo = new PageInfo<>(content, total, pageNum, pageSize);
        return pageInfo;
    }
}
