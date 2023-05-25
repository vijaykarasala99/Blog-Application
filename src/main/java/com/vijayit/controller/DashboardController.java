package com.vijayit.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.vijayit.binding.CommentsForm;
import com.vijayit.binding.CreatePostForm;
import com.vijayit.entity.Comments;
import com.vijayit.entity.CreatePostDetails;
import com.vijayit.repo.CommentsRepo;
import com.vijayit.repo.CreatePostDetailsRepo;
import com.vijayit.service.PostCommentService;

@Controller
public class DashboardController {

	@Autowired
	private PostCommentService service;

	@Autowired
	private CreatePostDetailsRepo repo;

	@Autowired
	private HttpSession session;

	@Autowired
	private CommentsRepo commentsRepo;

	@GetMapping("/logout")
	public String logout() {

		session.invalidate();

		return "index";
	}
		@GetMapping("/post")
	public String createPost(Model model) {

		model.addAttribute("newpost", new CreatePostForm());

		return "postpage";

	}

	@PostMapping("/post")
	public String savePost(@ModelAttribute("newpost") CreatePostForm form, Model model) {

		String status = service.savePost(form);

		model.addAttribute("newpost", new CreatePostForm());

		model.addAttribute("msg", status);

		return "postpage";

	}

	@GetMapping("/edit")
	private String EditPost(@RequestParam("postId") Integer postId, Model model) {

		System.out.println(postId);

		Optional<CreatePostDetails> findById = repo.findById(postId);

		if (findById.isPresent()) {

			CreatePostDetails createPostDetails = findById.get();

			model.addAttribute("newpost", createPostDetails);
			model.addAttribute("hidden", postId);

		}

		return "postpage";
	}

	@PostMapping("/comment")
	public String giveComment(@ModelAttribute("comments") CommentsForm form, Model model) {

		Integer blogId = (Integer) session.getAttribute("blogId");

		String commentAdded = service.addComment(form, blogId);

		model.addAttribute("msg", commentAdded);


		// same code is avaliable in blog controller
		Optional<CreatePostDetails> findBlog = repo.findById(blogId);

		model.addAttribute("display", findBlog.get());

		model.addAttribute("reviews", findBlog.get().getComments());

		model.addAttribute("comments", new CommentsForm());

		return "blogpage";

	}

	@GetMapping("/showcomments")
	public String getComments(Model model) {

		List<Comments> retrieveComments = service.retrieveComments();

		model.addAttribute("commentsList", retrieveComments);

		return "commentsPage";

	}

	@GetMapping("/deletecomment")
	public String deleteComment(@RequestParam("commentId") Integer commentId) {

		commentsRepo.deleteById(commentId);

		// redirectAttributes.addFlashAttribute("message", "Comment deleted
		// successfully");
		return "redirect:/showcomments";

	}

	@GetMapping("/delete")
	public String softDeletePost(@RequestParam("postId") Integer blogId) {

		service.softDelete(blogId);
		return "redirect:/dashboard";
	}

	@GetMapping("/dashboard")
	public String dashboradPage(Model model) {

		 List<CreatePostDetails> posts = service.getPosts();
		 
		 
		 List<CreatePostDetails> collect = posts.stream().filter(results -> results.getDeleted() == 0)
					.collect(Collectors.toList());


		System.out.println(collect.size());
		
		model.addAttribute("blogs", collect);

		return "dashboardpage";

	}

	@GetMapping("/search")

	public String search(@RequestParam("title") String title, Model model) {

		List<CreatePostDetails> searchResults = repo.findByTitleContaining(title);
		
		List<CreatePostDetails> collect = searchResults.stream().filter(results -> results.getDeleted() == 0)
		.collect(Collectors.toList());

		model.addAttribute("blogs", collect);

		return "dashboardpage";

	}


}
