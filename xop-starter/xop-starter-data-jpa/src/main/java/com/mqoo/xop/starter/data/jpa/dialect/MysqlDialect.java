package com.mqoo.xop.starter.data.jpa.dialect;

import java.util.List;

/**
 * mysql数据库方言实现类
 * 
 * @author mingqi.wang
 * @since 2017/08/02
 */
public class MysqlDialect extends AbstractDialect {

    @Override
    public String handlerPagingSQL(String sql, int page, int size, List<Object> params) {
        LOG.debug("SQL before page handle:{}", sql);
        StringBuffer pageSql = new StringBuffer(sql);
        pageSql.append(" limit ?,? ");
        if (size > 0) {
            int firstResult = page * size;
            params.add(firstResult);
            params.add(size);
        }
        String newSql = pageSql.toString();
        LOG.debug("SQL after page handle:{}", newSql);
        return newSql;
    }

    @Override
    public Database supportDatabase() {
        return Dialect.Database.MYSQL;
    }
}
