package com.vijayit.service;

import com.vijayit.binding.LoginForm;
import com.vijayit.binding.RegistrationForm;




public interface UserService {

	public boolean userSignup(RegistrationForm signup);

	public boolean loginVerify(LoginForm login);

}
