package com.vijayit.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Entity
@Data
public class CreatePostDetails {
	
	@Id
	@GeneratedValue
	@Column(name="Post_Id")
	private Integer postId;
	
	
	@Column(name="Title")
	private String title;
	
	@Column(name="Description")
	private String description;
	
	@Column(name="Content")
	@Lob
	private String content;
	
	@CreationTimestamp
	private LocalDate createdDate;
	
	
	
	@UpdateTimestamp
	private LocalDate updateOn;
	
	
	@Column(name = "deleted")
    private Integer deleted =0;
	
	
	
	//relationship with user
	  @ManyToOne
	  @JoinColumn(name = "userId")
	  private UserRegistration userRegistration;
	
	 //relationship with comment
	@OneToMany(cascade = CascadeType.REMOVE,fetch =FetchType.EAGER,mappedBy ="createPostDetails")
	private List<Comments> comments ;


	
	
	
	
	
	
	
}
