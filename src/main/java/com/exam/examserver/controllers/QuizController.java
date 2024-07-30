package com.exam.examserver.controllers;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exam.examserver.DTO.PaginationDTO;
import com.exam.examserver.models.exam.Category;
import com.exam.examserver.models.exam.Quiz;
import com.exam.examserver.services.QuizeService;

@RestController
@CrossOrigin("*")
@RequestMapping("/quiz")
public class QuizController {
	
	@Autowired
	private QuizeService quizeService;
	
	
	//add quiz
	@PostMapping("/")
	public ResponseEntity<Quiz> addQuiz(@RequestBody Quiz quiz){
		return ResponseEntity.ok(quizeService.addQuiz(quiz));
	}
	
	//update quiz
	@PutMapping("/")
	public ResponseEntity<Quiz> updateQuiz(@RequestBody Quiz quiz){
		return ResponseEntity.ok(quizeService.updateQuiz(quiz));
	}
	
	//get all quizzes
	@GetMapping("/")
	public ResponseEntity<PaginationDTO> getQuizzes(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", required = false) Integer pageSize){
		PaginationDTO paginationDTO = this.quizeService.getQuizes(pageNumber,pageSize);
		return new ResponseEntity<PaginationDTO>(paginationDTO, HttpStatus.OK);
	}
	
	//get single quiz
	@GetMapping("{quizId}")
	public Quiz getQuiz(@PathVariable("quizId") Long quizId) {
		return this.quizeService.getQuiz(quizId);
	}
	
	//delete quiz
	@DeleteMapping("/{quizId}")
	public void deleteQuiz(@PathVariable("quizId") Long quizId){
		this.quizeService.deleteQuiz(quizId);
	}
	
	@GetMapping("/category/{cid}")
	public List<Quiz> getQuizOfCategory(@PathVariable("cid") Long cid) {
		Category category = new Category();
		category.setCid(cid);
		return this.quizeService.getQuizOfCategory(category);
	}
	
	@GetMapping("/active")
	public List<Quiz> getActiveQuizzes(){
		return this.quizeService.getActiveQuizzes();
	}
	
	@GetMapping("/category/active/{cid}")
	public List<Quiz> getActiveQuizzesOfCategory(@PathVariable("cid") Long cid){
		Category category = new Category();
		category.setCid(cid);
		return this.quizeService.getActiveQuizOfCategory(category);
	}
	
	
}
