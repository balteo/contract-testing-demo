package org.example.contracttestingdemo.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.ObjectError;

import java.util.List;

public class ValidationException extends RuntimeException {

    @Getter
    @Setter
    private List<ObjectError> errors;

    @Getter
    @Setter
    private String errorMessage;

}
