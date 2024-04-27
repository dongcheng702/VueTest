package com.vuetest.springboot.cms.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vuetest.springboot.cms.entity.VueTestBean;
import com.vuetest.springboot.cms.form.storeform.StoreForm;
import com.vuetest.springboot.cms.mapper.VueTestMapper;

@Service
public class VueTestServiceImpl implements VueTestService {

	@Autowired
	private VueTestMapper mapper;
	
	private int insCount;
	
	private List<VueTestBean> csvList;

	/**
	 * 增删改查
	 */
	@Override
	public List<VueTestBean> selectWithPagination(StoreForm form) {
		return mapper.selectWithPagination(form);
	}
	@Override
	public int selectCount() throws Exception {
		int ret = mapper.selectCount();
		return ret;
	}
	@Override
	public int selectIdMax() throws Exception {
		int ret = mapper.selectIdMax();
		return ret;
	}
	@Override
	public List<VueTestBean> selectAll() {
		return mapper.selectAll();
	}
	@Override
	public int add(StoreForm form) {
		return mapper.addData(form);
	}
	@Override
	public int updata(StoreForm form) {
		return mapper.updata(form);	
	}
	@Override
	public int delDatas(List<Integer> id) {
		return mapper.deletes(id);
	}
	@Override
	public int delete(StoreForm form) {
		return mapper.delete(form);
	}
	/**
	 *  上传文件到数据库
	 */
	@Override
	public int registCsvToMySql(byte[] bytes, int count) throws Exception {
		insCount = 0;

		String data = new String(bytes);

		splitData(data, count);

		return insCount;
	}

	private void splitData(String data,int count) throws Exception {
		csvList = new ArrayList<VueTestBean>();
		data = data.replaceAll("\"", "");
		String[] itemValues = data.split("\\r\\n");
		if(count != itemValues[0].split(",").length){
			throw new Exception("項目件数は正しくありません。" + "正しい項目件数は：" + itemValues[0].split(",").length);
		}
		
		if(itemValues.length <= 1){
			throw new Exception("空白ファイルです。");
		}
		
				
		for(int i = 1;i < itemValues.length;i++) {
			VueTestBean bean = saveToList(itemValues[i]);
			if (bean != null) {
				csvList.add(bean);
			}
		}
		
		if (csvList.size() > 0) {
			insCount = mapper.insertBulk(csvList);
		}
		
	}
	
	private VueTestBean saveToList(String data) throws Exception {
		String[] itemValues = data.split(",");

		VueTestBean bean = new VueTestBean();
		bean.setStoreId(itemValues[0]);
		bean.setStoreName(itemValues[1]);
		bean.setAddress(itemValues[2]);
		bean.setPhone(itemValues[3]);
		bean.setStartDay(itemValues[4]);
		bean.setFinishDay(itemValues[5]);
		bean.setRegistDay(itemValues[6]);
		bean.setUpdateDay(itemValues[7]);

		return bean;
	}

	// 时间格式处理
	private Timestamp FormatToTimestamp(String dt) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/M/d H:m:s");
		Date parsedDate;
		try {
			parsedDate = dateFormat.parse(dt);
		} catch (ParseException e) {
			e.printStackTrace(); // 打印异常信息，以便调试
			return null;
		}
		return new java.sql.Timestamp(parsedDate.getTime());
	}
	@Override
	
	public List<VueTestBean> selectId(List<Integer> id) throws Exception {
		return mapper.selectId(id);
	}


}
