package com.vijayit.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class UserRegistration {

	@Id
	@GeneratedValue
	private int userId;

	@Column(name = "First_Name")
	private String firstName;

	@Column(name = "Last_Name")
	private String lastName;

	@Column(name = "Email")
	private String email;

	@Column(name = "Password")
	private String password;

	
	
	// relationship with post
	@OneToMany(cascade = CascadeType.ALL,fetch =FetchType.EAGER,mappedBy ="userRegistration")
	private List<CreatePostDetails> createPostDetails;

}
