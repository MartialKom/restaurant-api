package com.groupe1.restaurant.handlers;

import com.groupe1.restaurant.exception.ObjectValidationException;
import com.groupe1.restaurant.exception.OperationNonPermittedException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
@Component
public class GlobalExceptionHandler {

    @ExceptionHandler({ObjectValidationException.class})
    public ResponseEntity<ExceptionRepresentation> handleException(ObjectValidationException exception) {
        ExceptionRepresentation representation = ExceptionRepresentation.builder()
                .errorMessage("Object not valid exception has occurred")
                .status(HttpStatus.BAD_REQUEST.value())
                .validationErrors(exception.getViolations())
                .build();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(representation);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionRepresentation> handleException(EntityNotFoundException exception) {
        ExceptionRepresentation representation = ExceptionRepresentation.builder()
                .errorMessage(exception.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .build();
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(representation);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ExceptionRepresentation> handleException(NoSuchElementException exception) {
        ExceptionRepresentation representation = ExceptionRepresentation.builder()
                .errorMessage(exception.getMessage())
                .status(HttpStatus.NO_CONTENT.value())
                .build();
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(representation);
    }

    @ExceptionHandler(OperationNonPermittedException.class)
    public ResponseEntity<ExceptionRepresentation> handleException(OperationNonPermittedException exception) {
        ExceptionRepresentation representation = ExceptionRepresentation.builder()
                .errorMessage(exception.getErrorMsg())
                .status(HttpStatus.NOT_ACCEPTABLE.value())
                .build();
        return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .body(representation);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ExceptionRepresentation> handleException(DataIntegrityViolationException exception) {
        ExceptionRepresentation representation = ExceptionRepresentation.builder()
                .errorMessage(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(representation);
    }

}
