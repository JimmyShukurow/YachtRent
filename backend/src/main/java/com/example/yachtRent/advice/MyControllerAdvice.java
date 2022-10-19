package com.example.yachtRent.advice;


import com.example.yachtRent.exception.InvalidCredentialsException;
import com.example.yachtRent.exception.UserIsMissingException;
import com.example.yachtRent.exception.YachtIsMissingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyControllerAdvice {
    @ExceptionHandler(YachtIsMissingException.class)
    public ResponseEntity<String> handleMissingYacht(YachtIsMissingException yachtIsMissingException) {

        return new ResponseEntity<String>("Yacht does not exits", HttpStatus.BAD_REQUEST);

    }
    @ExceptionHandler(UserIsMissingException.class)
    public ResponseEntity<String> handleMissingUser(UserIsMissingException userIsMissingException) {

        return new ResponseEntity<String>("Wrong username or password. Or user does not exits", HttpStatus.BAD_REQUEST);

    }
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<String> handleMissingUser(InvalidCredentialsException invalidCredentialsException) {

        return new ResponseEntity<String>("Wrong username or password.", HttpStatus.BAD_REQUEST);

    }

}
