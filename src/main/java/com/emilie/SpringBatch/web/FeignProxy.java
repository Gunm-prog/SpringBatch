package com.emilie.SpringBatch.web;

import com.emilie.SpringBatch.model.Book;
import com.emilie.SpringBatch.model.Loan;
import com.emilie.SpringBatch.model.User;
import com.emilie.SpringBatch.model.UserAccountJwt;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name= "feignClient", url="localhost:8181")
public interface FeignProxy {

    @PostMapping("/authenticate")
    String login(@RequestBody UserAccountJwt userAccountJwt);

    @GetMapping("/api/v1/loans/delayList")
    List<Loan> getLoanDelayLoan(@RequestHeader("Authorization") String accessToken);

    @GetMapping("/api/loans/updateStatus")
    Long updateLoanStatus(@RequestHeader("Authorization") String accessToken);

    @GetMapping("/api/user/{id}")
    User retrieveUser(@PathVariable("id") Long id, @RequestHeader("Authorization") String accessToken);

    @GetMapping("/api/v1/books/{id}")
    Book retrieveBook(@PathVariable("id") Long id, @RequestHeader("Authorization") String accessToken);

    @GetMapping("/api/copies/available/book/{bookId}")
    boolean checkIfCopyAvailableForBook(@PathVariable("bookId") Long bookId, @RequestHeader("Authorization") String accessToken);

    @GetMapping("/api/v1/copies/availableCopies/book/{bookId}")
    Long numberOfCopyAvailableForBook(@PathVariable("bookId")  Long bookId, @RequestHeader("Authorization") String accessToken);


}