package org.example.sijalsystem;

import org.assertj.core.api.Assertions;
import org.example.sijalsystem.Model.*;
import org.example.sijalsystem.Repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.*;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    InterviewAnalysisByAiRepository interviewAnalysisByAiRepository;


    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    InterviewSessionRepository interviewSessionRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CardRepository cardRepository;

    InterviewSession session;
    Question question1, question2;

    InterviewAnalysisByAi aiAnalysis;
    InterviewWithHR interviewWithHR;
    InterviewAnalysisByHR hrAnalysis;


    @BeforeEach
    void setUp() {

        //Customer
        User user = new User();
        user.setName("Muath");
        userRepository.save(user);

        Customer customer = new Customer();
        customer.setUser(user);
        customerRepository.save(customer);

        //Interview Session
        session = new InterviewSession();
        session.setCustomer(customer);
        session.setStatus("CREATED");
        session = interviewSessionRepository.save(session);

        //Questions
        question1 = new Question();
        question1.setInterviewSession(session);
        question1.setText("What is Spring Boot?");

        question2 = new Question();
        question2.setInterviewSession(session);
        question2.setText("Explain REST APIs.");

        //AI Analysis
        aiAnalysis = new InterviewAnalysisByAi();
        aiAnalysis.setInterviewSession(session);
        aiAnalysis.setFinalScore(85);

        //card


        //HR Analysis
        hrAnalysis = new InterviewAnalysisByHR();
        hrAnalysis.setInterviewWithHR(interviewWithHR);
        hrAnalysis.setFinalScore(70);
    }



    @Test
    public void findByInterviewSession_Id() {
        interviewAnalysisByAiRepository.save(aiAnalysis);

        InterviewAnalysisByAi result = interviewAnalysisByAiRepository.findByInterviewSession_Id(session.getId());

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getInterviewSession().getId()).isEqualTo(session.getId());
    }


    @Test
    public void findByInterviewSession_Customer_Id() {

        // Arrange (جاهز من @BeforeEach): session + customer + user
        interviewAnalysisByAiRepository.save(aiAnalysis);

        // Act
        List<InterviewAnalysisByAi> result = interviewAnalysisByAiRepository.findByInterviewSession_Customer_Id(session.getCustomer().getId());

        // Assert
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isNotEmpty();
        Assertions.assertThat(result.get(0).getInterviewSession().getCustomer().getId()).isEqualTo(session.getCustomer().getId());
    }

    @Test
    public void findCardById() {

        // Arrange
        Card card = new Card();
        card.setNumber("1234-5678-9012-3456"); // أي قيمة مطلوبة عندك
        card = cardRepository.save(card);

        // Act
        Card result = cardRepository.findCardById(card.getId());

        // Assert
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getId()).isEqualTo(card.getId());
        Assertions.assertThat(result.getNumber()).isEqualTo("1234-5678-9012-3456");
    }


    /* ================= QuestionRepository ================= */

    @Test
    public void findByInterviewSession_Id_Questions() {
        questionRepository.save(question1);
        questionRepository.save(question2);

        List<Question> questions = questionRepository.findByInterviewSession_Id(session.getId());

        Assertions.assertThat(questions).hasSize(2);
        Assertions.assertThat(questions.get(0).getInterviewSession().getId()).isEqualTo(session.getId());
    }

    @Test
    public void findQuestionById() {
        questionRepository.save(question1);

        Question result = questionRepository.findQuestionById(question1.getId());

        Assertions.assertThat(result).isEqualTo(question1);
    }
}