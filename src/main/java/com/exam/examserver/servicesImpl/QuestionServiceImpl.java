package com.exam.examserver.servicesImpl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.exam.examserver.models.exam.Questions;
import com.exam.examserver.models.exam.Quiz;
import com.exam.examserver.repository.QuestionRepository;
import com.exam.examserver.services.QuestionService;

@Service
public class QuestionServiceImpl implements QuestionService{
	
	@Autowired
	private QuestionRepository questionRepository;
	
	@Override
	public Questions addQuestion(Questions question) {
		return this.questionRepository.save(question);
	}

	@Override
	public Questions updateQuestion(Questions question) {
		return this.questionRepository.save(question);
	}

	@Override
	public Set<Questions> getQuestions() {
		return new HashSet<>(this.questionRepository.findAll());
	}

	@Override
	public Questions getQuestion(Long questionId) {
		return this.questionRepository.findById(questionId).get();
	}

	@Override
	public Set<Questions> getQuestionsOfQuiz(Quiz quiz) {
		return this.questionRepository.findByQuiz(quiz);
	}

	@Override
	public void deleteQuestion(Long quesId) {
		Questions question = new Questions();
		question.setQid(quesId);
		this.questionRepository.delete(question);
		
	}

	@Override
	public List<String> processPdfFile(File file, String quizId) throws IOException {
		PDDocument document = null;
		List<Map<String,String>> listOfData1 = new ArrayList<>();
		List<String> data = new ArrayList<>();
		HashMap<String, String> data1 = new HashMap<>();
		try {
			document = PDDocument.load(file);
			System.out.println(document);
			PDFTextStripper stripper = new PDFTextStripper();
			String pdfText = stripper.getText(document);
			String[] lines = pdfText.split("\\r?\\n");
			StringBuilder questionBuilder = new StringBuilder();
			boolean inQuestion = false;

			for (String line : lines) {
				line = line.trim();
				
				// Adjust the condition based on how questions are formatted in your PDF
				if (line.endsWith("?")) {
				     if (inQuestion) {
	                        // Add the previous question to listOfData1
	                       // data1.put("question", questionBuilder.toString().trim());
	                        listOfData1.add(new HashMap<>(data1));

	                        // Clear StringBuilder and data1 for next question
	                        questionBuilder.setLength(0);
	                        data1.clear();
	                    }

	                    // Extract question number and content
	                    int spaceIndex = line.indexOf(" ");
	                    String key = line.substring(0, spaceIndex).trim();
	                    String value = line.substring(spaceIndex).trim();
	                    data1.put("Question", value);

	                    // Build question string
	                    questionBuilder.append(line).append("\n");
	                    inQuestion = true;
				} else {
					if (inQuestion) {
						// Extract key and value for choices or additional lines of question
                        int spaceIndex = line.indexOf(" ");
                        if (spaceIndex != -1) {
                            String key = line.substring(0, spaceIndex).trim();
                            String value = line.substring(spaceIndex).trim();
                            data1.put(key, value);
                        } else {
                            // Append to existing question
                            questionBuilder.append(line).append("\n");
                        }
					}
				}
			}

			// Add the last question
			if (!data1.isEmpty()) {
                data1.put("question", questionBuilder.toString().trim());
                listOfData1.add(new HashMap<>(data1));
            }
			
			for(Map<String, String> map :listOfData1) {
				Questions question = new Questions();
				String sub = quizId.substring(8, 10);
				Quiz quiz = new Quiz();
				quiz.setQid(Long.parseLong(sub));
				for(Map.Entry<String, String> entry:map.entrySet()) {
					
					question.setQuiz(quiz);
					if(entry.getKey().contains("Question")) {
						question.setContent(entry.getValue());
					}else if(entry.getKey().contains("Answer")) {
						String a = entry.getValue().replace(":", "");
						question.setAnswer(a);
					}else if(entry.getKey().contains("a)")) {
						question.setOption1(entry.getValue());
					}
					else if(entry.getKey().contains("b)")) {
						question.setOption2(entry.getValue());
					}
					else if(entry.getKey().contains("c)")) {
						question.setOption3(entry.getValue());
					}else {
						question.setOption4(entry.getValue());
					}
				}
				questionRepository.save(question);
			}
			

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			document.close();
		}
		System.out.println("data :" + listOfData1);
		return data;
	}

	
	
	

}
