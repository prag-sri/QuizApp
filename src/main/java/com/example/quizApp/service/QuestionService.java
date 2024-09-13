package com.example.quizApp.service;

import com.example.quizApp.model.Question;
import com.example.quizApp.dao.QuestionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
        } catch(Exception e){
            e.printStackTrace();
        }
        // if something goes wrong, you can return an empty array
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        return new ResponseEntity<>(questionDao.findByCategory(category), HttpStatus.OK);
    }

    public ResponseEntity<String> addQuesion(Question question) {
        questionDao.save(question);
        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    public ResponseEntity<String> deleteQuestionById(Integer id) {
        questionDao.deleteById(id);
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }

    public ResponseEntity<String> updateQuestionById(Question question) {
        // fetch question
        Optional<Question> oldQuestion= questionDao.findById(question.getId());

        // check if question's id is present or not
        if(oldQuestion.isEmpty())
            return new ResponseEntity<>("Question not found", HttpStatus.NOT_FOUND);

        // update old question with new question fields
        Question newQuestion= new Question();

        // don't change unmodifiable fields
        newQuestion.setId(question.getId());

        // change modifiable fields
        newQuestion.setCategory(question.getCategory());
        newQuestion.setQuestionTitle(question.getQuestionTitle());
        newQuestion.setOption1(question.getOption1());
        newQuestion.setOption2(question.getOption2());
        newQuestion.setOption3(question.getOption3());
        newQuestion.setOption4(question.getOption4());
        newQuestion.setRightAnswer(question.getRightAnswer());
        newQuestion.setDifficultyLevel(question.getDifficultyLevel());

        questionDao.save(newQuestion);

        return new ResponseEntity<>("Updated", HttpStatus.OK);
    }
}
