package org.oauth2.shopapp.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.oauth2.shopapp.dto.request.UserDTO;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, UserDTO> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(UserDTO userDTO, ConstraintValidatorContext context) {
       boolean isValid = userDTO.getRetypePassword().equals(userDTO.getPassword());
       if(!isValid){
           context.disableDefaultConstraintViolation();
           context.buildConstraintViolationWithTemplate("Retype not Match")
                   .addPropertyNode("RepeatPassword")
                   .addConstraintViolation();
       }

       return isValid;

    }
}
