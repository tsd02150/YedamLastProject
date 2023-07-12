package com.yedam.app.common.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.example.demo.util.file.FileUtilities;

@Controller
public class EditController {
	@PostMapping("/ckeditor/fileUpload")
	public String fileUploadFromCKEditor(HttpServletResponse response, MultipartHttpServletRequest multipartRequest) throws Exception {

		PrintWriter printWriter = null;

		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");

		try {
			String fileName = FileUtilities.uploadCKEditorFile(multipartRequest.getFile("upload"), "files/ckeditor");
			String fileUrl = "/common/ckeditor/fileDownload?fileName=" + fileName; 
			
			printWriter = response.getWriter();
			// �꽌踰꾨줈 �뙆�씪 �쟾�넚 �썑 �씠誘몄� �젙蹂� �솗�씤�쓣 �쐞�빐 filename, uploaded, fileUrl �젙蹂대�� response �빐二쇱뼱�빞 �븿
			printWriter.println("{\"filename\" : \"" + fileName + "\", \"uploaded\" : 1, \"url\":\"" + fileUrl + "\"}");
			printWriter.flush();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (printWriter != null) {
				printWriter.close();
			}
		}

		return null;
	}
}
