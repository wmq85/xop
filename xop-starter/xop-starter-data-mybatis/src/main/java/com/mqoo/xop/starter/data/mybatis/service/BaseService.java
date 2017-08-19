package com.mqoo.xop.starter.data.mybatis.service;

import java.io.Serializable;
import java.util.List;

import com.github.pagehelper.PageInfo;
import com.mqoo.platform.xop.common.data.BaseEntity;
import com.mqoo.platform.xop.common.data.PageRequest;

import tk.mybatis.mapper.entity.Example;

/**
 * 基础 service接口
 * <p>
 * 单表service接口需要继承此接口
 * 
 * @author mingqi.wang
 * @since 2017/7/4
 * @param <T> Entity
 * @param <ID> ID
 */
public interface BaseService<T extends BaseEntity, ID extends Serializable> {
    /**
     * 所有数据列表
     * 
     * @param o
     * @return List<T>
     */
    public List<T> getAll();

    /**
     * 分页获取所有数据
     * 
     * @param pageable
     * @return PageInfo<T>
     */
    public PageInfo<T> getAll(PageRequest pageable);

    /**
     * 分页条件查询
     * 
     * @param example
     * @param pageable
     * @see tk.mybatis.mapper.entity.Example
     * @return PageInfo<T>
     */
    public PageInfo<T> pageList(Example example, PageRequest pageable);

    /**
     * 条件查询
     * 
     * @param example
     * @see tk.mybatis.mapper.entity.Example
     * @return List<T>
     */
    public List<T> selectList(Example example);

    /**
     * 根据ID获取对象
     * 
     * @param id
     * @return 对象<T>
     */
    public T getById(ID id);

    /**
     * 根据ID删除对象
     * 
     * @param id
     */
    public void deleteById(ID id);

    /**
     * 保存对象
     * <p>
     * 如果ID存在则更新，否则新增
     * 
     * @param o
     */
    public void save(T o);
}
