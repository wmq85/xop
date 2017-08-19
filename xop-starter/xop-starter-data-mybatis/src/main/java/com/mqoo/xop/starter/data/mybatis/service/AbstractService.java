package com.mqoo.xop.starter.data.mybatis.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mqoo.platform.xop.common.data.BaseEntity;
import com.mqoo.platform.xop.common.data.PageRequest;
import com.mqoo.xop.starter.data.mybatis.dao.BaseMapper;

import tk.mybatis.mapper.entity.Example;

/**
 * 基础 service 抽象实现类
 * <p>
 * 单表service实现类需要继承此抽象类
 * 
 * @author mingqi.wang
 * @since 2017/7/4
 * @param <T> Entity
 * @param <ID> ID
 * @param <DAO> Mapper
 */
@Transactional(readOnly = false)
public abstract class AbstractService<T extends BaseEntity, ID extends Serializable, DAO extends BaseMapper<T>>
        implements BaseService<T, ID> {

    @Autowired
    protected DAO dao;

    @Transactional(readOnly = true)
    @Override
    public List<T> getAll() {
        return dao.selectAll();
    }

    @Transactional(readOnly = true)
    @Override
    public PageInfo<T> getAll(PageRequest pageable) {
        if (pageable.getPage() != null && pageable.getRows() != null) {
            PageHelper.startPage(pageable.getPage(), pageable.getRows());
        }
        List<T> content = dao.selectAll();
        PageInfo<T> page = new PageInfo<T>(content);
        return page;
    }

    @Transactional(readOnly = true)
    @Override
    public PageInfo<T> pageList(Example example, PageRequest pageable) {
        if (pageable.getPage() != null && pageable.getRows() != null) {
            PageHelper.startPage(pageable.getPage(), pageable.getRows());
        }
        List<T> content = dao.selectByExample(example);
        PageInfo<T> page = new PageInfo<T>(content);
        return page;
    }

    @Transactional(readOnly = true)
    @Override
    public List<T> selectList(Example example) {
        return dao.selectByExample(example);
    }

    @Transactional(readOnly = true)
    @Override
    public T getById(ID id) {
        return dao.selectByPrimaryKey(id);
    }

    @Override
    public void deleteById(ID id) {
        dao.deleteByPrimaryKey(id);
    }

    @Override
    public void save(T o) {
        if (o.getId() != null) {
            dao.updateByPrimaryKey(o);
        } else {
            dao.insert(o);
        }
    }
}
