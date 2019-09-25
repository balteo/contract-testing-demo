package org.example.contracttestingdemo.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.ObjectError;

import java.util.List;

@AllArgsConstructor
public class ValidationException extends RuntimeException {

    @Getter
    @Setter
    private List<ObjectError> errors;
}
