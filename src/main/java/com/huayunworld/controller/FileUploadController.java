package com.huayunworld.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("web")

public class FileUploadController {
	public Logger logger = Logger.getLogger(this.getClass());

	@RequestMapping("upload")
	public void uploadPicture(@RequestParam("file") MultipartFile file, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String time = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			String path = getWebPath(request) + "upload" + "\\" + time + "\\";
			// 创建目录
			File dir = new File(path);
			FileUtils.forceMkdir(dir);

			// 生成6为随机数的文件名
			Integer fileNumber = (int) ((Math.random() * 9 + 1) * 100000);
			// 获取文件名后缀
			String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
			String fileName = fileNumber.toString() + suffix;

			String filePath = path + fileName;
			File newFile = new File(filePath);
			file.transferTo(newFile);

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("filePath", newFile.toString());
			jsonObject.put("status", "success");

			response.getWriter().print(jsonObject.toString());
		} catch (Exception e) {
			logger.debug("上传文件异常", e);
		}
	}

	public String getWebPath(HttpServletRequest request) {
		String path = request.getSession().getServletContext().getRealPath("/");
		return path;
	}

}