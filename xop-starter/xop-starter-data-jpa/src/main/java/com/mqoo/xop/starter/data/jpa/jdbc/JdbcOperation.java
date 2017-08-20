package com.mqoo.xop.starter.data.jpa.jdbc;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

/**
 * JDBC操作接口类
 * 
 * @author mingqi.wang
 * @since 2017/08/02
 */
public interface JdbcOperation {
    /**
     * sql查询
     * 
     * @param sql
     * @param params
     * @param clz
     * @return
     */
    public <E> List<E> find(String sql, Object[] params, Class<E> clz);

    /**
     * sql执行
     * <p>
     * 可以执行insert update delete语句
     * 
     * @param sql
     * @param params
     * @return
     */
    public int excute(String sql, Object[] params);

    /**
     * sql分页查询
     * 
     * @param sql
     * @param parameters
     * @param pageNo
     * @param pageSize
     * @param entity
     * @return
     */
    public <E> Page<E> find(String sql, Object[] parameters, int pageNo, int pageSize,
            Class<E> entity);

    /**
     * 根据sql查询单个对象
     * 
     * @param sql 查询对象sql语句
     * @param args 绑定参数
     * @param classT 注意该参数，jdbcTemplate.queryForObject传入的不能是自定义的classType， 如果是自定义的，需要经过new
     *        BeanPropertyRowMapper<E>(classT)转换，默认支持的只有比如String，int等类型
     * @param <E>
     * @return
     */
    public <E> E findObject(String sql, Object[] args, Class<E> clz);
    //
    // public Map<String,Object> find(String sql,Object[] params);

    public List<Map<String, Object>> find(String sql, Object[] params);

    public Page<Map<String, Object>> find(String sql, Object[] parameters, int pageNo,
            int pageSize);
}
