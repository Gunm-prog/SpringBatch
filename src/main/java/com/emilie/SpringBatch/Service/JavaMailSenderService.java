package com.emilie.SpringBatch.Service;

import com.emilie.SpringBatch.model.Loan;
import com.emilie.SpringBatch.model.LoanEmail;
import com.emilie.SpringBatch.web.FeignProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@EnableScheduling
@EnableAsync
public class JavaMailSenderService {

    private final JavaMailSender javaMailSender;
    private final SimpleMailMessage preConfiguredMessage;
    private final FeignProxy feignProxy;



    @Autowired
    public JavaMailSenderService(JavaMailSender javaMailSender, SimpleMailMessage preConfiguredMessage, FeignProxy feignProxy) {
        this.javaMailSender = javaMailSender;
        this.preConfiguredMessage = preConfiguredMessage;
        this.feignProxy = feignProxy;
    }

    /**
     * This method retrieve all delays loans by calling the MS library-api and send a mail to all customers
     */
    public void sendRecoveryMail (List<Loan> loansList){
//        logger.info("test mail sender");
        List<LoanEmail> loanEmails = new ArrayList<>();

        for (Loan loan : loansList){

            LoanEmail loanEmail = new LoanEmail();
            loanEmail.setUserDto(loan.getUserDto());
            loanEmail.setBookDto(loan.getCopyDto().getBookDto());
            loanEmail.setExpectedBookReturn(loan.getLoanEndDate().toInstant().atZone( ZoneId.systemDefault()).toLocalDate());

            String message = "the return date of your loan for the book " + loan.getCopyDto().getBookDto().getTitle() + " has passed.";

            if(loan.isExtended()){
                System.out.println(loan.getId() + " delay and non renewable ");
                message += "you cannot renew your loan anymore.";
            }else {
                System.out.println(loan.getId() + " delay but renewable ");
                message += "you can renew your loan.";
            }

            loanEmail.setMessage(message);
            loanEmails.add(loanEmail);
        }

        for (LoanEmail loanEmail : loanEmails){
            sendSimpleMessage(
                    loanEmail.getUserDto().getEmail(),
                    "delay of your loan.",
                    loanEmail.getMessage()
            );

        }

    }



    /**
     * This method will send a pre-configured message
     * @param argTo the email of the recipient
     * @param argFirst the firstName of the recipient
     * @param argLast the lastName of the recipient
     * @param argTitle the title of the book
     * @param date the date of the expected return
     *
     * */
    private void sendPreConfiguredMail(String argTo, String argFirst, String argLast, String argTitle, String date){
        SimpleMailMessage mailMessage = new SimpleMailMessage(preConfiguredMessage);
        String text = String.format( Objects.requireNonNull(mailMessage.getText()),argFirst, argLast, argTitle, date);
        mailMessage.setTo(argTo);
        mailMessage.setText(text);
        javaMailSender.send(mailMessage);
    }


    private void sendSimpleMessage(String to, String subject, String body) {
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom( "lib7@batch.com" );
        message.setTo( to );
        message.setSubject( subject );
        message.setText( body );
        System.out.println( message );
        javaMailSender.send( message );
    }

    /**
     * This method format the expected return date
     *
     * @param date a date
     * @return a formatted date
     */
    private String formatDateToMail(LocalDate date){
        String pattern = "dd MMM yyyy";
        return date.format( DateTimeFormatter.ofPattern(pattern));
    }


}

