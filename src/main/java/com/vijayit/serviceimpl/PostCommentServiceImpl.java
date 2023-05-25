package com.vijayit.serviceimpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vijayit.binding.CommentsForm;
import com.vijayit.binding.CreatePostForm;
import com.vijayit.entity.Comments;
import com.vijayit.entity.CreatePostDetails;
import com.vijayit.entity.UserRegistration;
import com.vijayit.repo.CommentsRepo;
import com.vijayit.repo.CreatePostDetailsRepo;
import com.vijayit.repo.UserRegistrationRepo;
import com.vijayit.service.PostCommentService;

@Service

public class PostCommentServiceImpl implements PostCommentService {

	@Autowired
	private CreatePostDetailsRepo postRepo;

	@Autowired
	private UserRegistrationRepo userRepo;

	@Autowired
	private HttpSession session;

	@Autowired
	private CommentsRepo commentRepo;

	@Override
	public String savePost(CreatePostForm form) {

		Integer userId = (Integer) session.getAttribute("userId");

		Optional<UserRegistration> findById2 = userRepo.findById(userId);

		UserRegistration userDetails = findById2.get();

		if (form.getPostId() != null) {

			Optional<CreatePostDetails> findById = postRepo.findById(form.getPostId());

			CreatePostDetails updatedDetails = findById.get();

			updatedDetails.setTitle(form.getTitle());

			updatedDetails.setTitle(form.getTitle());

			updatedDetails.setDescription(form.getDescription());

			postRepo.save(updatedDetails);

			return "records updated sucessfully";

		}

		CreatePostDetails entity = new CreatePostDetails();

		BeanUtils.copyProperties(form, entity);

		entity.setUserRegistration(userDetails);

		postRepo.save(entity);

		return "post saved sucessfully";
	}

	@Override
	public List<CreatePostDetails> getPosts() {

		Integer userId = (Integer) session.getAttribute("userId");

		// TODO: get the userdetails

		Optional<UserRegistration> findById = userRepo.findById(userId);

		UserRegistration userDetails = findById.get();

		// TODO: get the posts of logged in user

		List<CreatePostDetails> totalPosts = userDetails.getCreatePostDetails();

		return totalPosts;
	}

	@Override
	public String addComment(CommentsForm form, Integer blogId) {

		// TODO: copy the form data to entity

		Comments entity = new Comments();

		BeanUtils.copyProperties(form, entity);

		// TODO: get post id and using that retrieve the post details , set to the
		// entity

		Optional<CreatePostDetails> postDetails = postRepo.findById(blogId);

		CreatePostDetails createPostDetails = postDetails.get();

		entity.setCreatePostDetails(createPostDetails);

		// TODO: finally save the comments

		commentRepo.save(entity);

		return "Your Comment is added";
	}

	@Override
	public List<Comments> retrieveComments() {

		Integer userId = (Integer) session.getAttribute("userId");

		Optional<UserRegistration> findById = userRepo.findById(userId);
		UserRegistration userDetails = findById.get();
		List<CreatePostDetails> createPostDetails = userDetails.getCreatePostDetails();

		List<Comments> listComments = userDetails.getCreatePostDetails().stream()
				.flatMap(post -> post.getComments().stream()).collect(Collectors.toList());

		return listComments;

	}

	// soft deleting

	@Override
	public void softDelete(Integer blogId) {

		Optional<CreatePostDetails> postDetails = postRepo.findById(blogId);
		if (postDetails.isPresent()) {
			CreatePostDetails createPostDetails = postDetails.get();
			createPostDetails.setDeleted(1);

			postRepo.save(createPostDetails);
		}

	}

	// retrieve only not soft deleted posts

	public List<CreatePostDetails> getActivePersons() {
		return postRepo.findByDeletedFalse();
	}

}
