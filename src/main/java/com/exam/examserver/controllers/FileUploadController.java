package com.exam.examserver.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.exam.examserver.helper.FileUploadHelper;
import com.exam.examserver.models.exam.Questions;
import com.exam.examserver.services.QuestionService;

@RestController
@CrossOrigin("*")
@RequestMapping("/upload-file")
public class FileUploadController {
	
	@Autowired
	private FileUploadHelper fileUploadHelper;
	
	
	
	@PostMapping("/")
	public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file,@RequestParam("quiz") String quizId){
		System.out.println(file.getOriginalFilename());
		System.out.println(file.getSize());
		System.out.println(file.getName());
		
		try {
			if (file.isEmpty()) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Please upload a file");
			}
			if (!file.getContentType().equals("application/pdf")) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Only pdf file allowed");
			}

			boolean isFileUploded = fileUploadHelper.uploadFile(file,quizId);
			//System.out.println("pdfData"+pdfData);
			if (isFileUploded) {
				return ResponseEntity.ok("File uploaded Successfully..");
				
			}
		} catch (Exception e) {
			System.out.println("Error in catch: "+e.getMessage());
		}
		
		return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong in uploded file !! please try again");
	}
}
