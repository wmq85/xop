package com.mqoo.xop.starter.data.jpa.dialect;

import java.util.List;

/**
 * 数据库方言接口类
 * 
 * @author Donald
 * @author mingqi.wang
 * @since 2017/08/02
 */
public interface Dialect {
    
    /**
     * 将传入的SQL做分页处理
     * 
     * @param String
     *            sql 原SQL
     * @param int page 第几页，从0开始
     * @param int size 每页数量
     */
    public String handlerPagingSQL(String sql, int page, int size,List<Object> params);
    
    /**
     * 处理sql,计算分页总条数
     * <p>
     * 先移除order by语句，再加上select count(1)..
     * 
     * @param sql 原sql
     * @return 处理后的countSQL
     */
    public String handlerCountSQL(String sql);
    
    /**
     * 支持的数据库类型
     * @return
     */
    public Database supportDatabase();
    
    
    public enum Database{
        ORACLE,MYSQL
    }
}
