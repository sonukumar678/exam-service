package com.exam.examserver.services;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import com.exam.examserver.DTO.PaginationDTO;
import com.exam.examserver.models.exam.Category;
import com.exam.examserver.models.exam.Quiz;

public interface QuizeService {
	
	public Quiz addQuiz(Quiz quiz);
	
	public Quiz updateQuiz(Quiz quiz);
	
	public PaginationDTO getQuizes(Integer pageNumber, Integer pageSize);
	
	public Quiz getQuiz(Long quizId);
	
	public void deleteQuiz(Long quizId);

	public List<Quiz> getQuizOfCategory(Category category);
	
	public List<Quiz> getActiveQuizzes();
	
	public List<Quiz> getActiveQuizOfCategory(Category c);


	
}
