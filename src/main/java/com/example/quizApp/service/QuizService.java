package com.example.quizApp.service;

import com.example.quizApp.dao.QuestionDao;
import com.example.quizApp.dao.QuizDao;
import com.example.quizApp.model.Question;
import com.example.quizApp.model.QuestionWrapper;
import com.example.quizApp.model.Quiz;
import com.example.quizApp.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;

    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<String> createQuiz(String category, int numQ, String quizTitle) {
        // get a list of questions by category
        List<Question> questionList= questionDao.findRandomQuestionsByCategory(category, numQ);

        Quiz quiz= new Quiz();
        quiz.setTitle(quizTitle);
        quiz.setQuestion(questionList);

        quizDao.save(quiz);

        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        Optional<Quiz> quiz= quizDao.findById(id);

        // getting list of questions
        List<Question> questionsFromDB= quiz.get().getQuestion();

        // converting quiz to question wrapper
        List<QuestionWrapper> questionsForUser= new ArrayList<>();
        for(Question q: questionsFromDB){
            QuestionWrapper qw= new QuestionWrapper(
                   q.getId(),
                   q.getQuestionTitle(),
                   q.getOption1(),
                   q.getOption2(),
                   q.getOption3(),
                   q.getOption4()
            );
            questionsForUser.add(qw);
        }

        return new ResponseEntity<>(questionsForUser, HttpStatus.OK);
    }

    public ResponseEntity<Integer> submitQuiz(Integer id, List<Response> responses) {
        // get the quiz
        Quiz quiz= quizDao.findById(id).get();

        // get the questions
        List<Question> questions= quiz.getQuestion();

        // calculate score
        int index=0;  // index of questions list
        int score=0;
        for(Response response: responses){
                if(response.getId().equals(questions.get(index).getId())){
                    if(response.getResponse().equals(questions.get(index).getRightAnswer()))
                        score++;
                }
                index++;
            }

        return new ResponseEntity<>(score, HttpStatus.OK);
    }
}
