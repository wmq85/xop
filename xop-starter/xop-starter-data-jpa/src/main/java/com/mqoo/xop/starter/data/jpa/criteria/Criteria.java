package com.mqoo.xop.starter.data.jpa.criteria;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

/** 
 * JPA条件查询容器 
 * 
 * @param <T> 查询实体泛型
 * @author mingqi.wang
 * @since 2017/08/02
 */  
public class Criteria<T> implements Specification<T> {
    private List<Criterion> criterions = new ArrayList<Criterion>();  
  
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query,  
            CriteriaBuilder builder) {  
        if (!criterions.isEmpty()) {  
            List<Predicate> predicates = new ArrayList<Predicate>();  
            for(Criterion c : criterions){  
                predicates.add(c.toPredicate(root, query,builder));  
            }  
            // 将所有条件用 and 联合起来 
            if (predicates.size() > 0) {  
                return builder.and(predicates.toArray(new Predicate[predicates.size()]));  
            }  
        }  
        return builder.conjunction();  
    }  
    /** 
     * 增加简单条件表达式 
     * @param criterion
     */  
    public Criteria<T> add(Criterion criterion){  
        
        if(criterion!=null){  
            criterions.add(criterion);  
        }
        
        return this;
    }  
    
    /**
     * 构建器
     * @return 查询容器 Criteria<T>
     */
    public static <T> Criteria<T> build(){
        return new Criteria<T>();
    }
}