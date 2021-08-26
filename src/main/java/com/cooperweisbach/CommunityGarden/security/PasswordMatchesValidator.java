package com.cooperweisbach.CommunityGarden.security;

import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
@Slf4j
public class PasswordMatchesValidator
        implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){

        MemberDTO user = (MemberDTO) obj;
        log.warn(user.getPassword());
        log.warn(user.getMatchingPassword());
        return user.getPassword().equals(user.getMatchingPassword());
    }
}
