package com.telusko.quizapp.service;

import com.telusko.quizapp.dao.QuestionDao;
import com.telusko.quizapp.dao.QuizDao;
import com.telusko.quizapp.model.Question;
import com.telusko.quizapp.model.QuestionWrapper;
import com.telusko.quizapp.model.Quiz;
import com.telusko.quizapp.model.Response;
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

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        List<Question> questions= questionDao.findRandomQuestionsByCategory(category, numQ);

        Quiz quiz= new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionList(questions);
        quizDao.save(quiz);

        return new ResponseEntity<>("Success!", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        try{
            // fetch quiz by id
            Optional<Quiz> quiz= quizDao.findById(id);

            // get questions list of quiz
            List<Question> questionList= quiz.get().getQuestionList();

            // convert questions into question wrapper
            List<QuestionWrapper> questionWrapperList= new ArrayList<>();
            for(Question q: questionList){
                QuestionWrapper qw= new QuestionWrapper(
                        q.getId(),
                        q.getQuestionTitle(),
                        q.getOption1(),
                        q.getOption2(),
                        q.getOption3(),
                        q.getOption4()
                );

                // add the new question wrapper to the list
                questionWrapperList.add(qw);
            }

            return new ResponseEntity<>(questionWrapperList, HttpStatus.OK);
        } catch(Exception e){
            e.printStackTrace();
        }

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        Quiz quiz = quizDao.findById(id).get();

        List<Question> questionList= quiz.getQuestionList();

        int right=0;
        int index=0;
        for(Response r: responses){
            if(r.getRightAnswer().equals(questionList.get(index).getRightAnswer()))
                right++;
            index++;
        }

        return new ResponseEntity<>(right, HttpStatus.OK);
    }
}
