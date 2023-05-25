package com.vijayit.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vijayit.entity.UserRegistration;


public interface UserRegistrationRepo extends JpaRepository<UserRegistration,Integer> {

	
	public UserRegistration findByEmail(String Email);

	
	public UserRegistration findByEmailAndPassword(String email, String password);

	
}
