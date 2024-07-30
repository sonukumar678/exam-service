package com.exam.examserver.helper;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.exam.examserver.services.QuestionService;

@Component
public class FileUploadHelper {
	@Autowired
	private QuestionService questionService;
	
	//pdf-upload directory in local
	//public final String UPLOAD_DIR = "C:\\Users\\sonu_\\Project-PDF";
	//pdf-upload directory in local
	//public final String UPLOAD_DIR = "D:\\Project\\examserver\\examserver\\src\\main\\resources\\static\\pdf";
	//public final String UPLOAD_DIR = Paths.get("src/main/resources/static/pdf").toAbsolutePath().toString();
	public final String UPLOAD_DIR = new ClassPathResource("static/pdf/").getFile().getAbsolutePath();

	public FileUploadHelper() throws Exception{
		
	}
	
	public boolean uploadFile(MultipartFile multipartFile, String quizId) {
		boolean f = false;
		List<String> ls = new ArrayList<>();
		try {
			
			InputStream is = multipartFile.getInputStream();
			byte data[] = new byte[is.available()];
			
			File file = new File(UPLOAD_DIR+File.separator+multipartFile.getOriginalFilename());
			is.read(data);
			
			FileOutputStream fs = new FileOutputStream(UPLOAD_DIR+File.separator+multipartFile.getOriginalFilename());
			fs.write(data);
			fs.flush();
			fs.close();
			//process pdf file
			ls = this.questionService.processPdfFile(file,quizId);
			f=true;
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return f;
	}



}
