package com.emilie.SpringBatch.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;

@Configuration
public class JavaMailSender {

    @Bean
    public SimpleMailMessage emailRecoveryTemplate(){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo("%s");
        simpleMailMessage.setFrom("trinity.harris25@ethereal.email");
        simpleMailMessage.setSubject("\n" + "Book loan period exceeded - Dunkirk's Libraries");
        simpleMailMessage.setText("Hello, %s %s" +
                "\n\n\n" +
                "The return date for your book loan \"%s\" was : %s" +
                "\nYou have the option, if you have not already done so, to extend the term of your loan by 4 weeks." +
                "\n\n" + "Otherwise, remember to bring the book back to your library as soon as possible to avoid a penalty." +
                "\n\n\nDunkirk's Libraries" +
                "\n\n\n\n\n\n" + "This is an automatic send, please do not reply.");
        return simpleMailMessage;
    }
}

