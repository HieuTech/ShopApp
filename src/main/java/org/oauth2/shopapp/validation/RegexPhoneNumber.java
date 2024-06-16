package org.oauth2.shopapp.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.oauth2.shopapp.dto.request.UserDTO;
import org.oauth2.shopapp.entity.Users;

public class RegexPhoneNumber implements ConstraintValidator<RegexPhoneNumberValidator, Users> {


    @Override
    public void initialize(RegexPhoneNumberValidator constraintAnnotation) {
    }

    @Override
    public boolean isValid(Users users, ConstraintValidatorContext context) {
        String regex =     "/(84|0[3|5|7|8|9])+([0-9]{8})\b/g";
        boolean isValid = users.getPhoneNumber().matches(regex);
        if (!isValid){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Phone Number Is Not Valid")
                    .addPropertyNode("Phone Number")
                    .addConstraintViolation();
        }

        return isValid;
    }
}
