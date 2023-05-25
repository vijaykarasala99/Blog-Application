package com.vijayit.serviceimpl;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vijayit.binding.LoginForm;
import com.vijayit.binding.RegistrationForm;
import com.vijayit.entity.UserRegistration;
import com.vijayit.repo.UserRegistrationRepo;
import com.vijayit.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRegistrationRepo userRepo;

	@Autowired
	private HttpSession session;

	@Override
	public boolean userSignup(RegistrationForm signup) {

		UserRegistration findByEmail = userRepo.findByEmail(signup.getEmail());

		if (findByEmail != null) {
			return false;

		}

		UserRegistration entity = new UserRegistration();

		BeanUtils.copyProperties(signup, entity);

		userRepo.save(entity);

		return true;
	}

	@Override
	public boolean loginVerify(LoginForm form) {

		UserRegistration entity = userRepo.findByEmailAndPassword(form.getEmail(), form.getPassword());

		if (entity == null) {
			return false;
		}

		// create session and store user data in session

		session.setAttribute("userId", entity.getUserId());

		return true;

	}

}
