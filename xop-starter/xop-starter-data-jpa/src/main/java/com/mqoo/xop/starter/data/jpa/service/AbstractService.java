package com.mqoo.xop.starter.data.jpa.service;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.mqoo.platform.xop.common.data.PageInfo;
import com.mqoo.xop.starter.data.Repository;
import com.mqoo.xop.starter.data.jpa.dao.BaseRepository;

@Transactional(readOnly = true)
public abstract class AbstractService<T, ID extends Serializable, DAO extends BaseRepository<T, ID>>
        implements BaseService<T, ID> {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    protected DAO dao;

    @Override
    public List<T> findAll(Sort sort) {
        return Lists.newArrayList(dao.findAll(sort));
    }

    @Override
    public PageInfo<T> findAll(Pageable pageable) {
        Page<T> page = dao.findAll(pageable);
        return Repository.pageConvert(page);
    }

    @Transactional(readOnly = false)
    @Override
    public <S extends T> S save(S entity) {
        return dao.save(entity);
    }

    @Transactional(readOnly = false)
    @Override
    public <S extends T> List<S> save(Iterable<S> entities) {
        return Lists.newArrayList(dao.save(entities));
    }

    @Override
    public T findOne(ID id) {
        return dao.findOne(id);
    }

    @Override
    public boolean exists(ID id) {
        return dao.exists(id);
    }

    @Override
    public List<T> findAll() {
        return Lists.newArrayList(dao.findAll());
    }

    @Override
    public List<T> findAll(Iterable<ID> ids) {
        return Lists.newArrayList(dao.findAll(ids));
    }

    @Override
    public long count() {
        return dao.count();
    }

    @Transactional(readOnly = false)
    @Override
    public void delete(ID id) {
        dao.delete(id);
    }

    @Transactional(readOnly = false)
    @Override
    public void delete(T entity) {
        dao.delete(entity);
    }

    @Transactional(readOnly = false)
    @Override
    public void delete(Iterable<? extends T> entities) {
        dao.delete(entities);
    }

    @Transactional(readOnly = false)
    @Override
    public void deleteAll() {
        dao.deleteAll();
    }

    @Override
    public void flush() {
        dao.flush();
    }

    @Override
    public T findOne(Specification<T> spec) {
        return dao.findOne(spec);
    }

    @Override
    public List<T> findAll(Specification<T> spec) {
        return dao.findAll(spec);
    }

    @Override
    public PageInfo<T> findAll(Specification<T> spec, Pageable pageable) {
        Page<T> page = dao.findAll(spec, pageable);
        return Repository.pageConvert(page);
    }

    @Override
    public List<T> findAll(Specification<T> spec, Sort sort) {
        return dao.findAll(spec, sort);
    }

    @Override
    public long count(Specification<T> spec) {
        return dao.count(spec);
    }

    @Override
    public <S extends T> S findOne(Example<S> example) {
        return dao.findOne(example);
    }

    @Override
    public <S extends T> List<S> findAll(Example<S> example) {
        return Lists.newArrayList(dao.findAll(example));
    }

    @Override
    public <S extends T> List<S> findAll(Example<S> example, Sort sort) {
        return Lists.newArrayList(dao.findAll(example, sort));
    }

    @Override
    public <S extends T> PageInfo<S> findAll(Example<S> example, Pageable pageable) {
        Page<S> page = dao.findAll(example, pageable);
        return Repository.pageConvert(page);
    }

    @Override
    public <S extends T> long count(Example<S> example) {
        return dao.count(example);
    }

    @Override
    public <S extends T> boolean exists(Example<S> example) {
        return dao.exists(example);
    }
}
