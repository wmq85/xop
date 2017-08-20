package com.mqoo.xop.starter.data.jpa.criteria;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * JPA查询规则
 * 
 * @author mingqi.wang
 * @since 2017/08/02
 */
public interface Criterion {  
    
    public enum Operator {  
        ISNULL,NOTNULL,EQ, NE, ILIKE, LIKE, GT, LT, GTE, LTE,IN,NOTIN, AND, OR  
    }  
    
    public Predicate toPredicate(Root<?> root, CriteriaQuery<?> query,
                                 CriteriaBuilder builder);
}