package com.mqoo.platform.xop.example.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.mqoo.platform.xop.common.data.PageRequest;
import com.mqoo.platform.xop.common.web.api.response.BaseStatusSupportResponse;
import com.mqoo.platform.xop.example.api.entity.Country;
import com.mqoo.platform.xop.example.api.service.CountryService;
import com.mqoo.platform.xop.support.mvc.controller.BaseController;

import tk.mybatis.mapper.entity.Example;

/**
 * CRUD 示例
 * @author mingqi.wang
 *
 */
@RestController
@RequestMapping("/countries")
public class CountryController extends BaseController {

    @Autowired
    private CountryService countryService;

    @RequestMapping
    public BaseStatusSupportResponse<PageInfo<Country>> getAll(PageRequest page) {
        //List<Country> countryList = countryService.getAll(page);
    	//PageInfo<Country> pageResult= new PageInfo<Country>(countryList);
    	
    	Example example=new Example(Country.class);
    	example.orderBy("id").desc();
    	PageInfo<Country> pageResult=countryService.pageList(example, page);
        return BaseStatusSupportResponse.buildSuccessResponse(pageResult);
    }
    

    @RequestMapping(method = RequestMethod.POST)
    public BaseStatusSupportResponse<Country> add(@RequestBody Country country) {
    	countryService.save(country);
    	return BaseStatusSupportResponse.buildSuccessResponse(country);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public BaseStatusSupportResponse<Country> view(@PathVariable Long id) {
        Country country = countryService.getById(id);
        return BaseStatusSupportResponse.buildSuccessResponse(country);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public BaseStatusSupportResponse<?> delete(@PathVariable Long id) {
        countryService.deleteById(id);
        return BaseStatusSupportResponse.buildSuccessResponse();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public BaseStatusSupportResponse<Country> update(@RequestBody Country country) {
        countryService.save(country);
        return BaseStatusSupportResponse.buildSuccessResponse(country);
    }
}
