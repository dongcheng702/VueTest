package com.vuetest.springboot.cms.controller.storecontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import com.vuetest.springboot.cms.common.Constants;
import com.vuetest.springboot.cms.common.Result;
import com.vuetest.springboot.cms.entity.storeentity.StoreBean;
import com.vuetest.springboot.cms.form.storeform.StoreForm;
import com.vuetest.springboot.cms.interceptor.JwtInterceptor;
import com.vuetest.springboot.cms.service.storeservice.StoreService;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/store")
public class StoreController {
	@Autowired
	private StoreService service;
	@Autowired
	private JwtInterceptor jwtInterceptor;

	@GetMapping("/")
	public Result init() {
		return Result.success(service.selectAll());
	}

	@GetMapping("/selectIdMax")
	public Result selectIdMax() {
		try {
			int ret = service.selectIdMax();

			return Result.success(ret);
		} catch (Exception e) {

			return Result.success(0);
		}
	}
	
	@GetMapping("/selectCount")
	public Result selectCount() {
		try {
			int ret = service.selectCount();

			return Result.success(ret);
		} catch (Exception e) {

			return Result.success(0);
		}
	}
	
	@PostMapping("/select")
	public Result select(@RequestBody StoreForm form) {
		int page = (form.getPage() - 1) * 10;
		form.setPage(page);
		form.setPageSize(10);

		return Result.success(service.selectWithPagination(form));
	}
	
	@PostMapping("/delete")
	public Result delete(@RequestBody StoreForm form) {
		int ret = service.delete(form);
		if(ret == 1) {
			return Result.success(ret);	
		}
		return Result.error(Constants.CODE_600, "删除失败");
	}
	
	@PostMapping("/deletes")
	public Result deletes(@RequestBody List<Integer> id) {
		int ret = service.delDatas(id);
		if(ret > 0) {
			return Result.success(ret);	
		}
		return Result.error(Constants.CODE_600, "有" + (id.size() - ret) + "条数据删除失败");
	}

	@PostMapping("/updata")
	public Result updata(@RequestBody StoreForm form) {
		int ret = service.updata(form);
		if(ret == 1) {
			return Result.success(ret);	
		}
		return Result.error(Constants.CODE_600, "更新失败");
	}

	@PostMapping("/add")
	public Result add(@RequestBody StoreForm form) {
		int ret = service.add(form);
		if(ret == 1) {
			return Result.success(ret);	
		}
		return Result.error(Constants.CODE_600, "新增失败");
	}

	@PostMapping("/export")
	public void export(@RequestBody List<Integer> id, HttpServletResponse response) throws Exception {

		// 设置响应头禁用缓存
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "0");

		// 准备数据
		List<StoreBean> ret = service.selectId(id);

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
	public Result upfile(@RequestParam("file") MultipartFile file, StandardMultipartHttpServletRequest request) {
        String token = request.getHeader("token");
        // 进行 token 验证
        if (!jwtInterceptor.isTokenValid(token)) {
            return Result.error(Constants.CODE_600, "Token 验证失败");
        }
        
		int ret = 0;
		// 处理上传的文件
		if (!file.isEmpty()) {
			try {
				ret = service.registCsvToMySql(file.getBytes(), 8);
			} catch (Exception e) {
				return Result.error(Constants.CODE_600,"上传失败,主键冲突");
			}

			return Result.success(ret); // 成功处理文件
		} else {
			return Result.error(Constants.CODE_400,"文件为空"); // 文件为空
		}
	}

}
