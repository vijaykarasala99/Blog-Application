package com.vijayit.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Entity
@Data
public class Comments {

	@Id
	@GeneratedValue
	@Column(name = "Comment_Id")
	private int commentId;

	@Column(name = "Name")
	private String name;

	@Column(name = "Email")
	private String email;

	@Lob
	@Column(name = "Comment")
	private String comment;
	
	@CreationTimestamp
	private LocalDate createdDate;

	
	
	//relationship with post
	@ManyToOne
	@JoinColumn(name = "postId")
	private CreatePostDetails createPostDetails;

}
