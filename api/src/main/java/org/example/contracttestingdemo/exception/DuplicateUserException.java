package org.example.contracttestingdemo.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class DuplicateUserException extends RuntimeException {

    @Getter
    @Setter
    private String errorMessage;
}
