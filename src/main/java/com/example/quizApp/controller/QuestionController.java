package com.example.quizApp.controller;

import com.example.quizApp.model.Question;
import com.example.quizApp.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {

	@Autowired
	QuestionService questionService;

	@GetMapping("all")
	public ResponseEntity<List<Question>> getAllQuestions() {
			return questionService.getAllQuestions();
	}

	@GetMapping("category/{cat}")
	public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable("cat") String category){
		return questionService.getQuestionsByCategory(category);
	}

	@PostMapping("add")
	public ResponseEntity<String> addQuestion(@RequestBody Question question){
		return questionService.addQuesion(question);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<String> deleteQuestionById(@PathVariable Integer id){
		return questionService.deleteQuestionById(id);
	}

	@PutMapping("")
	public ResponseEntity<String> updateQuestionById(@RequestBody Question question){
		return questionService.updateQuestionById(question);
	}
}
