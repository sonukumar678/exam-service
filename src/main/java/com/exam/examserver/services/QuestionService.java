package com.exam.examserver.services;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import com.exam.examserver.models.exam.Questions;
import com.exam.examserver.models.exam.Quiz;



public interface QuestionService {
	
	public Questions addQuestion(Questions question);
	
	public Questions updateQuestion(Questions question);
	
	public Set<Questions> getQuestions();
	
	public Questions getQuestion(Long questionId);
	
	public Set<Questions> getQuestionsOfQuiz(Quiz quiz);
	
	public void deleteQuestion(Long quesId);

	public List<String> processPdfFile(File file, String quizId) throws IOException;

	



}
