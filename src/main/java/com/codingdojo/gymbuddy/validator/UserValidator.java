package com.codingdojo.gymbuddy.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.codingdojo.gymbuddy.models.Users;

@Component
public class UserValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Users.class.equals(clazz);
	}
	
	@Override
	public void validate(Object object, Errors errors) {
		Users users = (Users) object;
		if (!users.getPasswordConfirmation().equals(users.getPassword())) {
			errors.rejectValue("passwordConfirmation", "Match");
		}
	}
}
