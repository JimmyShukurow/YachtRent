package org.example.advice;


import org.example.exception.*;
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
    @ExceptionHandler(RoleIsMissingException.class)
    public ResponseEntity<String> handleMissingUser(RoleIsMissingException roleIsMissingException) {

        return new ResponseEntity<String>("There is no role like this", HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(RoleAlreadyAddedToUserException.class)
    public ResponseEntity<String> handleMissingUser(RoleAlreadyAddedToUserException roleAlreadyAddedToUserException) {

        return new ResponseEntity<String>("This user Already has this role", HttpStatus.BAD_REQUEST);

    }
    @ExceptionHandler(LinkHasExpiredException.class)
    public ResponseEntity<String> linkIsExpired(LinkHasExpiredException linkHasExpiredException) {

        return new ResponseEntity<String>("This link has expired", HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(UsernameIsAlreadyTakenException.class)
    public ResponseEntity<String> usernameIsTaken(UsernameIsAlreadyTakenException usernameIsAlreadyTakenException) {

        return new ResponseEntity<String>("This is name is already taken", HttpStatus.BAD_REQUEST);

    }
    @ExceptionHandler(PasswordIsNotComplexException.class)
    public ResponseEntity<String> passwordIsNotComplex(PasswordIsNotComplexException passwordIsNotComplexException) {

        return new ResponseEntity<String>("Your password is weak!", HttpStatus.BAD_REQUEST);

    }

}
