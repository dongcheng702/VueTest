package com.vuetest.springboot.cms.service;

import java.util.List;

public interface VueTestService {
	
	public int registCsvToMySql(byte[] bytes,int count) throws Exception;
	
	public int delDatas(List<Integer> id);
}
