package com.yedam.app.common.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.yedam.app.common.service.AttachFileService;
import com.yedam.app.common.service.AttachFileVO;
import com.yedam.app.common.service.DownloadS3;
import com.yedam.app.community.service.AttachVO;
import com.yedam.app.community.service.BoardVO;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class EditorController {
	
	@Autowired
	AttachFileService attachFileService;

	// ckEditor 이미지 등록
	@Value("${uploadImagePath}") // 프로퍼티 혹은 빈에 있는 값들을 들고올 때 사용 (Spring value로 import)
	public String uploadImagePath;
	
	@Value("${uploadAttachPath}") // 프로퍼티 혹은 빈에 있는 값들을 들고올 때 사용 (Spring value로 import)
	public String uploadAttachPath;

	@Value("${uploadRevieImagePath}") // 프로퍼티 혹은 빈에 있는 값들을 들고올 때 사용 (Spring value로 import)
	public String uploadRevieImagePath;
	
	private final AmazonS3 amazonS3;

	private final DownloadS3 downloadS3;
	
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

	@PostMapping(value = "/image/upload")
	public ModelAndView image(MultipartHttpServletRequest request) throws AmazonServiceException, SdkClientException, IOException {
		// modelandview를 사용하여 json 형식으로 보내기위해 모델앤뷰 생성자 매개변수로 jsonView 라고 써줌
		// jsonView 라고 쓴다고 무조건 json 형식으로 가는건 아니고 @Configuration 어노테이션을 단
		// WebConfig 파일에 MappingJackson2JsonView 객체를 리턴하는 jsonView 매서드를 만들어서 bean으로
		// 등록해야함
		ModelAndView mav = new ModelAndView("jsonView");

		// ckeditor 에서 파일을 보낼 때 upload : [파일] 형식으로 해서 넘어오기 때문에 upload라는 키의 밸류를 받아서
		// uploadFile에 저장함
		MultipartFile uploadFile = request.getFile("upload");

		// 업로드 파일의 본래 이름
		String originalName = uploadFile.getOriginalFilename();
			
		// 날짜 폴더 생성
		String folderPath = makeS3Folder("image");
		// UUID
		String uuid = UUID.randomUUID().toString();
		// 저장할 파일 이름 중간에 "_"를 이용하여 구분
		String saveName = folderPath + "/" + uuid + "_" + originalName;

		ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(uploadFile.getSize());
        metadata.setContentType(uploadFile.getContentType());

        amazonS3.putObject(bucket, saveName, uploadFile.getInputStream(), metadata);
        
		// ckeditor는 이미지 업로드 후 이미지 표시하기 위해 uploaded 와 url을 json 형식으로 받아야 함
		// uploaded, url 값을 modelandview를 통해 보냄
		mav.addObject("uploaded", true); // 업로드 완료
		mav.addObject("url", amazonS3.getUrl(bucket, saveName).toString()); // 업로드 파일의 경로

		return mav;
	}

	@PostMapping("/attach/upload")
	@ResponseBody
	public void attach(@RequestPart MultipartFile[] uploadFiles, AttachFileVO vo) throws AmazonServiceException, SdkClientException, IOException {
		for (MultipartFile uploadFile : uploadFiles) {
			// 업로드 파일의 본래 이름
			String originalName = uploadFile.getOriginalFilename();
				
			// 날짜 폴더 생성
			String folderPath = makeS3Folder("attach");
			// UUID
			String uuid = UUID.randomUUID().toString();
			// 저장할 파일 이름 중간에 "_"를 이용하여 구분
			String saveName = folderPath + "/" + uuid + "_" + originalName;

			ObjectMetadata metadata = new ObjectMetadata();
	        metadata.setContentLength(uploadFile.getSize());
	        metadata.setContentType(uploadFile.getContentType());

	        amazonS3.putObject(bucket, saveName, uploadFile.getInputStream(), metadata);
			
			vo.setAtchNm(saveName);
			vo.setAtchOriginNm(originalName);
			attachFileService.addBoardAttachFile(vo);
		}
	}
	
	@PostMapping("/attach/update")
	@ResponseBody
	public void attachUpdate(@RequestPart MultipartFile[] uploadFiles, AttachFileVO vo) throws AmazonServiceException, SdkClientException, IOException {

		for (MultipartFile uploadFile : uploadFiles) {
			// 업로드 파일의 본래 이름
			String originalName = uploadFile.getOriginalFilename();
			
			// 날짜 폴더 생성
			String folderPath = makeS3Folder("attach");
			// UUID
			String uuid = UUID.randomUUID().toString();
			// 저장할 파일 이름 중간에 "_"를 이용하여 구분
			String saveName = folderPath + "/" + uuid + "_" + originalName;
			
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentLength(uploadFile.getSize());
			metadata.setContentType(uploadFile.getContentType());
			
			amazonS3.putObject(bucket, saveName, uploadFile.getInputStream(), metadata);
			
			vo.setAtchNm(saveName);
			vo.setAtchOriginNm(originalName);
			attachFileService.addBoardAttachFile(vo);
		}
	}
	
	@GetMapping("downloadFile")
	public ResponseEntity<byte[]> downloadImage(String savename,String originname) throws IOException {

		return downloadS3.getObject(savename,originname);
	}
	

	@PostMapping(value = "/reviewImage/upload")
	@ResponseBody
	public Map<String, Object> reviewImage(MultipartHttpServletRequest request) throws AmazonServiceException, SdkClientException, IOException {
		// modelandview를 사용하여 json 형식으로 보내기위해 모델앤뷰 생성자 매개변수로 jsonView 라고 써줌
		// jsonView 라고 쓴다고 무조건 json 형식으로 가는건 아니고 @Configuration 어노테이션을 단
		// WebConfig 파일에 MappingJackson2JsonView 객체를 리턴하는 jsonView 매서드를 만들어서 bean으로
		// 등록해야함
		
		// ckeditor 에서 파일을 보낼 때 upload : [파일] 형식으로 해서 넘어오기 때문에 upload라는 키의 밸류를 받아서
		// uploadFile에 저장함
		MultipartFile uploadFile = request.getFile("upload");

		// 업로드 파일의 본래 이름
		String originalName = uploadFile.getOriginalFilename();
			
		// 날짜 폴더 생성
		String folderPath = makeS3Folder("reviewImage");
		// UUID
		String uuid = UUID.randomUUID().toString();
		// 저장할 파일 이름 중간에 "_"를 이용하여 구분
		String saveName = folderPath + "/" + uuid + "_" + originalName;

		ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(uploadFile.getSize());
        metadata.setContentType(uploadFile.getContentType());

        amazonS3.putObject(bucket, saveName, uploadFile.getInputStream(), metadata);
		

		Map<String, Object> map = new HashMap<>();
		// ckeditor는 이미지 업로드 후 이미지 표시하기 위해 uploaded 와 url을 json 형식으로 받아야 함
		// uploaded, url 값을 map를 통해 보냄
		map.put("uploaded", true); // 업로드 완료
		map.put("url", amazonS3.getUrl(bucket, saveName).toString()); // 업로드 파일의 경로

		return map;
	}

	// 저장 폴더 생성
	private String makeS3Folder(String uploadPath) {

		String folderPath = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
		// LocalDate를 문자열로 포멧	
		String resultPath = uploadPath+ "/" + folderPath;
		
		// File newFile= new File(dir,"파일명");
		if(!amazonS3.doesObjectExist(bucket, resultPath)) {
			amazonS3.putObject(bucket, resultPath + "/", new ByteArrayInputStream(new byte[0]), new ObjectMetadata());
		}
		
		return resultPath;
	}
	

	// 저장 폴더 생성
	private String makeFolder(String uploadPath) {

		String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
		// LocalDate를 문자열로 포멧
		String folderPath = str.replace("/", File.separator); // File.separator = 자바가 인식하는 경로 구분 형식 (/ ->
																// File.separator)
		File uploadPathFoler = new File(uploadPath, folderPath); // uploadPath 경로 / folderPath 폴더명
		// File newFile= new File(dir,"파일명");

		if (uploadPathFoler.exists() == false) {
			uploadPathFoler.mkdirs();
			// 만약 uploadPathFolder가 존재하지않는다면 makeDirectory하라는 의미입니다.
			// mkdir(): 디렉토리에 상위 디렉토리가 존재하지 않을경우에는 생성이 불가능한 함수
			// mkdirs(): 디렉토리의 상위 디렉토리가 존재하지 않을 경우에는 상위 디렉토리까지 모두 생성하는 함수
		}
		return folderPath;
	}
}
