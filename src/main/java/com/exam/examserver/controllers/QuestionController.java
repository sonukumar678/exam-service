package com.exam.examserver.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.exam.examserver.models.exam.Questions;
import com.exam.examserver.models.exam.Quiz;
import com.exam.examserver.services.QuestionService;
import com.exam.examserver.services.QuizeService;

@RestController
@CrossOrigin("*")
@RequestMapping("/question")
public class QuestionController {
	
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private QuizeService quizeService;

	
	//add question
	@PostMapping("/")
	public ResponseEntity<Questions> addQuestion(@RequestBody Questions question){
		return ResponseEntity.ok(this.questionService.addQuestion(question));
	}
	
	//update Question
	@PutMapping("/")
	public ResponseEntity<Questions> updateQuestion(@RequestBody Questions questions){
		return ResponseEntity.ok(this.questionService.updateQuestion(questions));
	}
	
	//get all question of any quiz
	@GetMapping("/quiz/{quizId}")
	public ResponseEntity<?> getQuestionsOfQuiz(@PathVariable("quizId") Long quizId){
		
		Quiz quiz = this.quizeService.getQuiz(quizId);
		Set<Questions> questions = quiz.getQuestions();
		List list= new ArrayList<>(questions);
		
		if(list.size() > Integer.parseInt(quiz.getNumberOfQuestions())) {
			list = list.subList(0, Integer.parseInt(quiz.getNumberOfQuestions()+1));
		}
		Collections.shuffle(list);
		return ResponseEntity.ok(list);
		
	}
	
	@GetMapping("/quiz/all/{quizId}")
	public ResponseEntity<?> getQuestionsOfQuizAdmin(@PathVariable("quizId") Long quizId){
		
		/*
		 * Quiz quiz = this.quizeService.getQuiz(quizId); Set<Questions> questions =
		 * quiz.getQuestions(); List list= new ArrayList<>(questions);
		 * 
		 * if(list.size() > Integer.parseInt(quiz.getNumberOfQuestions())) { list =
		 * list.subList(0, Integer.parseInt(quiz.getNumberOfQuestions()+1)); }
		 * Collections.shuffle(list); return
		 */ 
		Quiz quiz = new Quiz();
		quiz.setQid(quizId);
		Set<Questions> questionOfQuiz = this.questionService.getQuestionsOfQuiz(quiz);
		return ResponseEntity.ok(questionOfQuiz);
		
	}
	
	//get single question
	@GetMapping("/{quesId}")
	public Questions getQuestion(@PathVariable("quesId") Long quesId) {
		return this.questionService.getQuestion(quesId);
	}
	
	//delete question
	@DeleteMapping("/{quesId}")
	public void deleteQuestion(@PathVariable("quesId") Long quesId) {
		this.questionService.deleteQuestion(quesId);
	}

}
