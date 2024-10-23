package com.telusko.quizapp.service;

import com.telusko.quizapp.model.Question;
import com.telusko.quizapp.dao.QuestionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    QuestionDao questionDao;
    public ResponseEntity<List<Question>> getAllQuestions() {
        try{
            return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
        } catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        try{
            return new ResponseEntity<>(questionDao.findByCategory(category), HttpStatus.OK);
        } catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> addQuestion(Question question) {
        try{
            questionDao.save(question);
            return new ResponseEntity<>("Success!", HttpStatus.CREATED);
        } catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Failed!", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<String> updateQuestion(Integer id, Question question) {
        // fetch original question by id
        Question originalQuestion = questionDao.findById(id).orElse(new Question());

        // set all the values from updated into the original
        originalQuestion.setCategory(question.getCategory());
        originalQuestion.setQuestionTitle(question.getQuestionTitle());
        originalQuestion.setOption1(question.getOption1());
        originalQuestion.setOption2(question.getOption2());
        originalQuestion.setOption3(question.getOption3());
        originalQuestion.setOption4(question.getOption4());
        originalQuestion.setRightAnswer(question.getRightAnswer());
        originalQuestion.setDifficultyLevel(question.getDifficultyLevel());

        try{
            questionDao.save(originalQuestion);
            return new ResponseEntity<>("Updated!", HttpStatus.CREATED);
        } catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Failed!", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<String> deleteQuestion(Integer id) {
        try{
            questionDao.deleteById(id);
            return new ResponseEntity<>("Deleted!", HttpStatus.OK);
        } catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Failed!", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
