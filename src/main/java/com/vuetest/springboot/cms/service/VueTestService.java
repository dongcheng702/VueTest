package com.vuetest.springboot.cms.service;

import java.util.List;

import com.vuetest.springboot.cms.entity.VueTestBean;
import com.vuetest.springboot.cms.form.storeform.StoreForm;

public interface VueTestService {
	
	public int registCsvToMySql(byte[] bytes,int count) throws Exception;
	
	public int delDatas(List<Integer> id);
	
	public int delete(StoreForm form);
	
	public int updata(StoreForm form);
	
	public int add(StoreForm form);
	
	public List<VueTestBean> selectAll();
	
	public int selectIdMax() throws Exception;
	
	public int selectCount() throws Exception;
	
	public List<VueTestBean> selectId(List<Integer> id) throws Exception;
	
	public List<VueTestBean> selectWithPagination(StoreForm form);
}
