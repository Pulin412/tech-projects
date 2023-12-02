package com.cgm.application.answerToEverything.configuration;

import com.cgm.dao.IQuestionDAO;
import com.cgm.dao.QuestionDAO;
import com.cgm.service.AnswerToEverythingService;
import com.cgm.service.IAnswerToEverythingService;
import com.cgm.util.HibernateUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AnswerToEverythingAutoConfiguration {

    @Bean
    public IQuestionDAO questionDAO(){
        return new QuestionDAO(HibernateUtil.getSessionFactory());
    }

    @Bean
    public IAnswerToEverythingService answerToEverythingService(IQuestionDAO questionDAO){
        return new AnswerToEverythingService(questionDAO());
    }
}
