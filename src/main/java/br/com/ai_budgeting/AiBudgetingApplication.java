package br.com.ai_budgeting;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.beans.BeanProperty;

@SpringBootApplication
public class AiBudgetingApplication {



    public static void main(String[] args) {
        SpringApplication.run(AiBudgetingApplication.class, args);
    }

}
