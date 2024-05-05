package com.vuetest.springboot.cms.service.storeservice;

import java.util.List;

import com.vuetest.springboot.cms.entity.storeentity.StoreBean;
import com.vuetest.springboot.cms.form.storeform.StoreForm;

public interface StoreService {
	
	public int registCsvToMySql(byte[] bytes,int count) throws Exception;
	
	public int delDatas(List<Integer> id);
	
	public int delete(StoreForm form);
	
	public int updata(StoreForm form);
	
	public int add(StoreForm form);
	
	public List<StoreBean> selectAll();
	
	public int selectIdMax() throws Exception;
	
	public int selectCount() throws Exception;
	
	public List<StoreBean> selectId(List<Integer> id) throws Exception;
	
	public List<StoreBean> selectWithPagination(StoreForm form);
	
}
