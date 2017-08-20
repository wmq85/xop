package com.mqoo.xop.starter.data.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.mqoo.platform.xop.common.data.PageInfo;
import com.mqoo.xop.starter.data.Repository;
import com.mqoo.xop.starter.data.jdbc.dialect.AbstractDialect;
import com.mqoo.xop.starter.data.jdbc.dialect.Dialect;

/**
 * JDBC持久化实现类
 * 
 * @author mingqi.wang
 * @since 2017/08/02
 */
// @Repository
// @Primary
// @ConditionalOnProperty("spring.datasource.url")
// @Transactional(readOnly = true)
public class JdbcOperationImpl implements JdbcOperation {
    private Logger LOG = LoggerFactory.getLogger(this.getClass());

    private JdbcTemplate jdbcTemplate;
    private Environment environment;

    protected Dialect determineDialect() {
        String dataSourceUrl = environment.getProperty("spring.datasource.url");
        List<Dialect> dialects = AbstractDialect.regDialects();
        for (Dialect dialect : dialects) {
            if (StringUtils.containsIgnoreCase(dataSourceUrl, dialect.supportDatabase().name())) {
                LOG.debug("jdbc dao sql page handle support database:{}",
                        dialect.supportDatabase());
                return dialect;
            }
        }
        return null;
    }

    @Override
    public <E> List<E> find(String sql, Object[] params, Class<E> clz) {
        List<E> resultList = null;
        if (params != null && params.length > 0) {
            resultList = jdbcTemplate.query(sql, params, new BeanPropertyRowMapper<E>(clz));
        } else {
            // BeanPropertyRowMapper是自动映射实体类的
            resultList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<E>(clz));
        }
        return resultList;
    }


    @Override
    @Transactional
    public int excute(String sql, final Object[] params) {
        int num = 0;
        if (params == null || params.length == 0) {
            num = jdbcTemplate.update(sql);
        } else {
            num = jdbcTemplate.update(sql, new PreparedStatementSetter() {
                public void setValues(PreparedStatement ps) throws SQLException {
                    for (int i = 0; i < params.length; i++)
                        ps.setObject(i + 1, params[i]);
                }
            });
        }
        return num;
    }

    @Override
    public <E> PageInfo<E> find(String sql, Object[] parameters, int pageNo, int pageSize,
            Class<E> entity) {
        // 将SQL语句进行分页处理
        List<Object> params = Lists.newArrayList(parameters);
        Dialect dialect = determineDialect();
        String listSql = dialect.handlerPagingSQL(sql, pageNo, pageSize, params);
        String countSql = dialect.handlerCountSQL(sql);

        Object[] parametersNew = params.toArray();
        List<E> list = null;
        int total = 0;

        // 查询总记录数
        if (parameters == null || parameters.length <= 0) {
            total = jdbcTemplate.queryForObject(countSql, Integer.class);
        } else {
            total = jdbcTemplate.queryForObject(countSql, parameters, Integer.class);
        }

        // 查询列表数据
        if (parametersNew == null || parametersNew.length <= 0) {
            list = jdbcTemplate.query(listSql, new BeanPropertyRowMapper<E>(entity));
        } else {
            list = jdbcTemplate.query(listSql, parametersNew, new BeanPropertyRowMapper<E>(entity));
        }
        Page<E> page = new PageImpl<E>(list, new PageRequest(pageNo, pageSize), total);
        return Repository.pageConvert(page);
    }

    @Override
    public PageInfo<Map<String, Object>> find(String sql, Object[] parameters, int pageNo,
            int pageSize) {
        // 将SQL语句进行分页处理
        List<Object> params = Lists.newArrayList(parameters);
        Dialect dialect = determineDialect();
        String listSql = dialect.handlerPagingSQL(sql, pageNo, pageSize, params);
        String countSql = dialect.handlerCountSQL(sql);

        Object[] parametersNew = params.toArray();
        List<Map<String, Object>> list = null;
        int total = 0;

        // 查询总记录数
        if (parameters == null || parameters.length <= 0) {
            total = jdbcTemplate.queryForObject(countSql, Integer.class);
        } else {
            total = jdbcTemplate.queryForObject(countSql, parameters, Integer.class);
        }

        // 查询列表数据
        if (parametersNew == null || parametersNew.length <= 0) {
            list = jdbcTemplate.queryForList(listSql);
        } else {
            list = jdbcTemplate.queryForList(listSql, parametersNew);
        }
        Page<Map<String, Object>> page =
                new PageImpl<>(list, new PageRequest(pageNo, pageSize), total);
        return Repository.pageConvert(page);
    }

    @Override
    public <E> E findObject(String sql, Object[] args, Class<E> classT) {
        if (args == null || args.length <= 0) {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<E>(classT));
        }
        return jdbcTemplate.queryForObject(sql, args, new BeanPropertyRowMapper<E>(classT));
    }

    /*
     * public Map<String, Object> find(String sql, Object[] params) { return
     * jdbcTemplate.queryForMap(sql,params); }
     */

    @Override
    public List<Map<String, Object>> find(String sql, Object[] params) {
        if (params == null || params.length <= 0) {
            return jdbcTemplate.queryForList(sql);
        }
        return jdbcTemplate.queryForList(sql, params);
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
