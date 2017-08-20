package com.mqoo.platform.xop.example.dubbo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.mqoo.platform.xop.common.data.BaseEntity;

@Entity
@Table(name = "country")
public class Country extends BaseEntity {

    /**
     * IDENTITY方式ID主键配置
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * seq方式ID主键配置
     */
    // @Id
    // @Column(name = "id")
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    // //@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select
    // SQ_TEST_COUNTRY.nextval from dual")
    // private Long id;

    /**
     * 名称
     */
    private String countryname;

    /**
     * 代码
     */
    private String countrycode;

    /**
     * 获取名称
     *
     * @return countryname - 名称
     */
    public String getCountryname() {
        return countryname;
    }

    /**
     * 设置名称
     *
     * @param countryname 名称
     */
    public void setCountryname(String countryname) {
        this.countryname = countryname;
    }

    /**
     * 获取代码
     *
     * @return countrycode - 代码
     */
    public String getCountrycode() {
        return countrycode;
    }

    /**
     * 设置代码
     *
     * @param countrycode 代码
     */
    public void setCountrycode(String countrycode) {
        this.countrycode = countrycode;
    }

    @Override
    public Long getId() {
        return this.id;
    }
}
