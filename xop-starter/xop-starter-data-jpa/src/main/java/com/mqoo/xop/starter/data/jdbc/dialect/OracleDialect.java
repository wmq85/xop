package com.mqoo.xop.starter.data.jdbc.dialect;

import java.util.List;

/**
 * Oracle数据库方言实现类
 * 
 * @author mingqi.wang
 * @since 2017/08/02
 */
public class OracleDialect extends AbstractDialect {
    protected final static String PAGE_SQL_PREFIX = "select * from (select tb_.*,rownum num from (";
    protected final static String PAGE_SQL_END = ") tb_ where rownum<=?) where num>?";

    @Override
    public String handlerPagingSQL(String sql, int page, int size, List<Object> params) {
        LOG.debug("SQL before page handle:{}", sql);
        StringBuffer pageSql = new StringBuffer();
        pageSql.append(PAGE_SQL_PREFIX).append(sql).append(PAGE_SQL_END);
        if (size > 0) {
            int firstResult = page * size;
            params.add(firstResult + size);
            params.add(firstResult);
        }
        String newSql = pageSql.toString();
        LOG.debug("SQL after page handle:{}", newSql);
        return newSql;
    }

    @Override
    public Database supportDatabase() {
        return Dialect.Database.ORACLE;
    }
}
