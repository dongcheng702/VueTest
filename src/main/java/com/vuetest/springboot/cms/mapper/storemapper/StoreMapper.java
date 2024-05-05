package com.vuetest.springboot.cms.mapper.storemapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.vuetest.springboot.cms.entity.storeentity.StoreBean;
import com.vuetest.springboot.cms.form.storeform.StoreForm;

@Mapper
public interface StoreMapper {
	
	@Select("select storeId,storeName from market_store")
	List<StoreBean> selectAll();
	
	@Select("select max(CAST(storeId AS UNSIGNED)) from market_store")
	int selectIdMax();
	
	@Select("select COUNT(*) from market_store")
	int selectCount();
	
	@Select({
	    "<script>",
	    "select * from market_store",
	    "<where>",
	    "<if test='form.storeId != null and form.storeId != \"\"'>",
	    "and storeId = #{form.storeId}",
	    "</if>",
	    "<if test='form.storeName != null and form.storeName != \"\"'>",
	    "and storeName = #{form.storeName}",
	    "</if>",
	    "</where>",
	    "</script>"
	})
	List<StoreBean> select(@Param("form") StoreForm form);
	
	@Select({
	    "<script>",
	    "select * from market_store",
	    "<where>",
	    "<if test='form.storeId != null and form.storeId != \"\"'>",
	    "and storeId = #{form.storeId}",
	    "</if>",
	    "<if test='form.storeName != null and form.storeName != \"\"'>",
	    "and storeName like concat('%', #{form.storeName}, '%')", // 模糊查询条件
	    "</if>",
	    "</where>",
	    "limit #{form.page}, #{form.pageSize}", // 添加分页查询条件，注意小写的page和pageSize
	    "</script>"
	})
	List<StoreBean> selectWithPagination(@Param("form") StoreForm form);
	
	@Select({
	    "<script>",
	    "select * from market_store",
	    "<where>",
	    "<foreach item='storeId' collection='id' separator='or'>",
	    "(storeId = #{storeId})",
	    "</foreach>",
	    "</where>",
	    "</script>"
	})
	List<StoreBean> selectId(@Param("id") List<Integer> id);

	@Delete({
	    "<script>",
	    "delete from market_store",
	    "<where>",
	    "<if test='form.storeId != null and form.storeId != \"\"'>",
	    "and storeId = #{form.storeId}",
	    "</if>",
	    "<if test='form.storeName != null and form.storeName != \"\"'>",
	    "and storeName = #{form.storeName}",
	    "</if>",
	    "</where>",
	    "</script>"
	})
	int delete(@Param("form") StoreForm form);
	
	@Update({
	    "<script>",
	    "update market_store",
	    "<set>",
	    "storeName = #{form.storeName},",
	    "address = #{form.address},",
	    "phone = #{form.phone},",
	    "startDay = #{form.startDay},",
	    "finishDay = #{form.finishDay},",
	    "updateDay = NOW()",
	    "</set>",
	    "<where>",
	    "storeId = #{form.storeId}",
	    "</where>",
	    "</script>"
	})
	int updata(@Param("form") StoreForm form);
	
	
	@Insert({
	    "<script>",
	    "insert into market_store (storeId,storeName, address, phone, startDay, finishDay,registDay,updateDay) values",
	    "(#{form.storeId},#{form.storeName}, #{form.address}, #{form.phone}, #{form.startDay}, #{form.finishDay},NOW(),NOW())",
	    "</script>"
	})
	int addData(@Param("form") StoreForm form);

	
	@Insert({
	    "<script>",
	    "insert into market_store (storeId, storeName, address, phone, startDay, finishDay, registDay, updateDay) values",
	    "<foreach item='item' collection='csvList' separator=','>",
	    "(#{item.storeId}, #{item.storeName}, #{item.address}, #{item.phone}, #{item.startDay}, #{item.finishDay}, NOW(), NOW())",
	    "</foreach>",
	    "</script>"
	})
	int insertBulk(@Param("csvList") List<StoreBean> csvList);

	
	@Delete({
	    "<script>",
	    "delete from market_store",
	    "<where>",
	    "<foreach item='storeId' collection='id' separator='or'>",
	    "(storeId = #{storeId})",
	    "</foreach>",
	    "</where>",
	    "</script>"
	})
	int deletes(@Param("id") List<Integer> id);
	
}
