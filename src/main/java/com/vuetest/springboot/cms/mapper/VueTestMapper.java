package com.vuetest.springboot.cms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.vuetest.springboot.cms.entity.VueTestBean;
import com.vuetest.springboot.cms.form.storeform.StoreForm;

@Mapper
public interface VueTestMapper {
	
	@Select("select * from market_store")
	List<VueTestBean> selectAll();
	
	@Select({
	    "<script>",
	    "select * from market_store",
	    "<where>",
	    "<if test='form.storeId != null'>",
	    "and storeId = #{form.storeId}",
	    "</if>",
	    "<if test='form.storeName != null'>",
	    "and storeName = #{form.storeName}",
	    "</if>",
	    "</where>",
	    "</script>"
	})
	List<VueTestBean> select(@Param("form") StoreForm form);
	
	@Delete({
	    "<script>",
	    "delete from market_store",
	    "<where>",
	    "<if test='form.storeId != null'>",
	    "and storeId = #{form.storeId}",
	    "</if>",
	    "<if test='form.storeName != null'>",
	    "and storeName = #{form.storeName}",
	    "</if>",
	    "</where>",
	    "</script>"
	})
	int delete(@Param("form") StoreForm form);

}
