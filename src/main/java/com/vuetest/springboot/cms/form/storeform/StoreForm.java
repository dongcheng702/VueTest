package com.vuetest.springboot.cms.form.storeform;

import lombok.Data;

@Data
public class StoreForm {
	
	String storeId;
	String storeName;
	String address;
	String phone;
	String startDay;
	String finishDay;
	String registDay;
	String updateDay;
	
	int Page;
	int pageSize;
}
