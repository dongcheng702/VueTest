package com.vuetest.springboot.cms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vuetest.springboot.cms.entity.VueTestBean;
import com.vuetest.springboot.cms.form.storeform.StoreForm;
import com.vuetest.springboot.cms.mapper.VueTestMapper;

@RestController
@SpringBootApplication
public class VueTestController {
	
	@Autowired
	private VueTestMapper vuetestManager;
		
		@GetMapping("/")
	    public List<VueTestBean> init() { 
	        return vuetestManager.selectAll(); 
	    }
		
		
    @PostMapping("/select")
    public List<VueTestBean> select(@RequestBody StoreForm form) {
        // 在这里处理接收到的数据
    	List<VueTestBean> ret = vuetestManager.select(form);
    	
        return ret;
    }
    
    @PostMapping("/delete")
    public int delete(@RequestBody StoreForm form) {
        // 在这里处理接收到的数据
    	int ret = vuetestManager.delete(form);
    	
        return ret;
    }
}
