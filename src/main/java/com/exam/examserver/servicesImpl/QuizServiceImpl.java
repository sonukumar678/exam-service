package com.exam.examserver.servicesImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.exam.examserver.DTO.PaginationDTO;
import com.exam.examserver.models.exam.Category;
import com.exam.examserver.models.exam.Quiz;
import com.exam.examserver.repository.QuizRepository;
import com.exam.examserver.services.QuizeService;

@Service
public class QuizServiceImpl implements QuizeService{
	
	@Autowired
	private QuizRepository quizRepository;

	@Override
	public Quiz addQuiz(Quiz quiz) {
		return this.quizRepository.save(quiz);
	}

	@Override
	public Quiz updateQuiz(Quiz quiz) {
		return this.quizRepository.save(quiz);
	}

	@Override
	public PaginationDTO getQuizes(Integer pageNumber, Integer pageSize) {

		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<Quiz> pageQuiz = this.quizRepository.findAll(pageable);
		List<Quiz> content = pageQuiz.getContent();
		
		PaginationDTO paginationDTO = new PaginationDTO();
		paginationDTO.setContent(content);
		paginationDTO.setPageNumber(pageQuiz.getNumber());
		paginationDTO.setPageSize(pageQuiz.getSize());
		paginationDTO.setTotalElement(pageQuiz.getTotalElements());
		paginationDTO.setTotalPages(pageQuiz.getTotalPages());
		paginationDTO.setLastPage(pageQuiz.isLast());
		return paginationDTO;
	}

	@Override
	public Quiz getQuiz(Long quizId) {
		return this.quizRepository.findById(quizId).get();
	}

	@Override
	public void deleteQuiz(Long quizId) {
		this.quizRepository.deleteById(quizId);
		
	}

	@Override
	public List<Quiz> getQuizOfCategory(Category category) {
		// TODO Auto-generated method stub
		return this.quizRepository.findByCategory(category);
	}

	@Override
	public List<Quiz> getActiveQuizzes() {
		// TODO Auto-generated method stub
		return this.quizRepository.findByActive(true);
	}

	@Override
	public List<Quiz> getActiveQuizOfCategory(Category c) {
		// TODO Auto-generated method stub
		return this.quizRepository.findByCategoryAndActive(c, true);
	}



}
