package org.example.contracttestingdemo.validator;

import org.apache.commons.validator.routines.EmailValidator;
import org.example.contracttestingdemo.domain.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static org.springframework.validation.ValidationUtils.rejectIfEmptyOrWhitespace;

@Component
public class UserValidator implements Validator {

    private final static String EMAIL = "email";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        rejectIfEmptyOrWhitespace(errors, EMAIL, "email.required");
        rejectIfEmptyOrWhitespace(errors, FIRST_NAME, "firstName.required");
        rejectIfEmptyOrWhitespace(errors, LAST_NAME, "lastName.required");

        validateFirstName(errors, user.getFirstName());
        validateLastName(errors, user.getLastName());
        validateEmail(errors, user.getEmail());
    }

    private void validateEmail(Errors errors, String email) {
        EmailValidator emailValidator = EmailValidator.getInstance();
        if (!emailValidator.isValid(email)) {
            errors.rejectValue(EMAIL, "email.invalid");
        }
    }

    private void validateFirstName(Errors errors, String firstName) {
        if (firstName.length() < 2) {
            errors.rejectValue(FIRST_NAME, "firstName.min");
        }
    }

    private void validateLastName(Errors errors, String lastName) {
        if (lastName.length() < 2) {
            errors.rejectValue(LAST_NAME, "lastName.min");
        }
    }
}
