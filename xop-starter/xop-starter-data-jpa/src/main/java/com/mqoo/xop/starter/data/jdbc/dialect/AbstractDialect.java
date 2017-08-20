package com.mqoo.xop.starter.data.jdbc.dialect;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 数据库方言抽象实现类
 * 
 * @author Donald
 * @author mingqi.wang
 * @since 2017/08/02
 */
public abstract class AbstractDialect implements Dialect {
    static Logger LOG=LoggerFactory.getLogger(AbstractDialect.class);
    
    /**
     * regiest dialecs
     */
    private static List<Dialect> dialects=new ArrayList<>();
    
    static{
        MysqlDialect mysqlDialect=new MysqlDialect();
        OracleDialect oracleDialect=new OracleDialect();
        regDialect(mysqlDialect);
        regDialect(oracleDialect);
    }
    
    /**
     * 获取注册Dialect
     * @return Dialects
     */
    public static List<Dialect> regDialects(){
        return dialects;
    }

    /**
     * 处理sql,计算分页总条数
     * <p>
     * 先移除order by语句，再加上select count(1)..
     * 
     * @param sql 原sql
     * @return 处理后的countSQL
     */
    @Override
    public  String handlerCountSQL(String sql){
        LOG.debug("jdbc paging count sql before handle:{}",sql);
        String sqlWithoutOrder=StringUtils.replacePattern(sql, "(?i)order\\s+by\\s+.+$", "");
        String countSql=String.format("select count(1) from (%s) ta__", sqlWithoutOrder);
        LOG.debug("jdbc paging count sql after handle:{}",countSql);
        return countSql;
    }
    
    /**
     * 方言注册
     */
    private static void regDialect(Dialect dialect){
        dialects.add(dialect);
        LOG.info("persistence regiest dialect:{}",dialect.getClass().getName());
    }
}
