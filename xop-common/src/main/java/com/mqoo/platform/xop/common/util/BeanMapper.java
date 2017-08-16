package com.mqoo.platform.xop.common.util;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

import java.util.List;

/**
 * 简单封装orika, 实现深度转换Bean<->Bean的Mapper.
 */
public class BeanMapper {

    private static MapperFacade mapper = null;

    static {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mapper = mapperFactory.getMapperFacade();

    }

    /**
     * 转换对象的类型.
     */
    public static <K, V> V map(K source, Class<V> destinationClass) {
        return mapper.map(source, destinationClass);
    }


    /**
     * 对象复制.
     */
    public static <S, D> void copy(S sourceObject, D destinationObject) {
        mapper.map(sourceObject, destinationObject);
    }

    /**
     * 转换Collection中对象的类型.
     */
    public static <K, V> List<V> mapList(Iterable<K> sourceList, Class<V> destinationClass) {
        return mapper.mapAsList(sourceList, destinationClass);
    }

    public static MapperFacade getMapper() {
        return mapper;
    }
}
