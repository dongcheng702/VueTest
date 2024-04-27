package com.vuetest.springboot.cms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.vuetest.springboot.cms.entity.VueTestBean;
import com.vuetest.springboot.cms.form.storeform.StoreForm;
import com.vuetest.springboot.cms.mapper.VueTestMapper;
import com.vuetest.springboot.cms.service.VueTestService;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@SpringBootApplication
public class VueTestController {

	@Autowired
	private VueTestMapper vuetestManager;
	@Autowired
	private VueTestService service;

	@GetMapping("/")
	public List<VueTestBean> init() {
		return vuetestManager.selectAll();
	}

	@GetMapping("/selectIdMax")
	public int selectIdMax() {
		try {
			int ret = vuetestManager.selectIdMax();

			return ret;
		} catch (Exception e) {

			return 0;
		}
	}

	@GetMapping("/selectCount")
	public int selectCount() {
		try {
			int ret = vuetestManager.selectCount();

			return ret;
		} catch (Exception e) {

			return 0;
		}
	}

	@PostMapping("/select")
	public List<VueTestBean> select(@RequestBody StoreForm form) {

		// List<VueTestBean> ret = vuetestManager.select(form);
		int page = (form.getPage() - 1) * 10;
		form.setPage(page);
		form.setPageSize(10);
		List<VueTestBean> ret = vuetestManager.selectWithPagination(form);

		return ret;
	}
	
	@PostMapping("/delete")
	public int delete(@RequestBody StoreForm form) {

		int ret = vuetestManager.delete(form);

		return ret;
	}
	
	@PostMapping("/deletes")
	public int deletes(@RequestBody List<Integer> id) {

		int ret = service.delDatas(id);

		return ret;
	}

	@PostMapping("/updata")
	public int updata(@RequestBody StoreForm form) {

		int ret = vuetestManager.update(form);

		return ret;
	}

	@PostMapping("/add")
	public int add(@RequestBody StoreForm form) {

		int ret = vuetestManager.addData(form);

		return ret;
	}

	@PostMapping("/export")
	public void export(@RequestBody List<Integer> id, HttpServletResponse response) throws Exception {

		// 设置响应头禁用缓存
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "0");

		// 准备数据
		List<VueTestBean> ret = vuetestManager.selectId(id);

		// 创建 ExcelWriter 对象
		ExcelWriter writer = ExcelUtil.getWriter(true);

		// 添加标题别名
		writer.addHeaderAlias("storeId", "販売店ID");
		writer.addHeaderAlias("storeName", "店名");
		writer.addHeaderAlias("address", "住所");
		writer.addHeaderAlias("phone", "電話");
		writer.addHeaderAlias("startDay", "営業開始年月日");
		writer.addHeaderAlias("finishDay", "営業終了年月日");
		writer.addHeaderAlias("registDay", "登録日");
		writer.addHeaderAlias("updateDay", "更新日");

		// 一次性写出数据到 Excel
		writer.write(ret, true);

		// 设置响应内容类型
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

		// 设置响应头，指示浏览器以附件形式下载文件，并指定文件名
		response.setHeader("Content-Disposition", "attachment; filename=test.xlsx");

		// 获取响应输出流
		ServletOutputStream outputStream = response.getOutputStream();

		// 将 Excel 数据刷新到响应输出流
		writer.flush(outputStream, true);

		// 关闭输出流和 ExcelWriter 对象
		outputStream.close();
		writer.close();
	}

	@PostMapping("/upfile")
	public int upfile(@RequestParam("file") MultipartFile file) {
		int ret = 0;
		// 处理上传的文件
		if (!file.isEmpty()) {
			try {
				ret = service.registCsvToMySql(file.getBytes(), 8);
			} catch (Exception e) {
				return -1;
			}

			return ret; // 成功处理文件
		} else {
			return ret; // 文件为空
		}
	}

}
