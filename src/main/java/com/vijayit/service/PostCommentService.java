package com.vijayit.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.vijayit.binding.CommentsForm;
import com.vijayit.binding.CreatePostForm;
import com.vijayit.entity.Comments;
import com.vijayit.entity.CreatePostDetails;

@Component
	public interface PostCommentService {

	public String savePost (CreatePostForm form);
	
	public  List<CreatePostDetails>  getPosts();
	
	public String addComment (CommentsForm form, Integer blogId);
	
	public List<Comments> retrieveComments();
	
    public void softDelete(Integer blogId) ;
    
    public List<CreatePostDetails> getActivePersons() ;



}
