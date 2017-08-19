package com.mqoo.xop.starter.data.mybatis.dao;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * mybatis 基础mapper
 * <p>
 * 其他mapper都需要继承此接口<br>
 * 特别注意，该接口不能被扫描到，否则会出错
 * 
 * @author mingqi.wang
 * @since 2017/7/4
 * @param <T>
 */
public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T> {
}