package com.exam.examserver.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.examserver.models.exam.Questions;
import com.exam.examserver.models.exam.Quiz;

public interface QuestionRepository extends JpaRepository<Questions, Long>{

	Set<Questions> findByQuiz(Quiz quiz);

}
