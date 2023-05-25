package com.vijayit.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vijayit.binding.CommentsForm;
import com.vijayit.binding.LoginForm;
import com.vijayit.binding.RegistrationForm;
import com.vijayit.entity.CreatePostDetails;
import com.vijayit.repo.CreatePostDetailsRepo;
import com.vijayit.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService service;

	@Autowired
	private CreatePostDetailsRepo postRepo;

	@Autowired
	private HttpSession postSession;

	@GetMapping("/")
	public String loadIndexPage(Model model) {

		return "index";
	}

	@GetMapping("/allposts")
	public String allPosts(Model model) {

		List<CreatePostDetails> findAll = postRepo.findAll();

		model.addAttribute("bloglist", findAll);

		return "allpost";
	}

	@GetMapping("/blog")

	public String showBlog(@RequestParam Integer postId, Model model) {

		postSession.setAttribute("blogId", postId);

		Optional<CreatePostDetails> findBlog = postRepo.findById(postId);

		if (findBlog.isPresent()) {

			model.addAttribute("display", findBlog.get());

			model.addAttribute("comments", new CommentsForm());

			model.addAttribute("reviews", findBlog.get().getComments());

			return "blogpage";
		}

		return "index";

	}

	@GetMapping("/register")

	public String loadRegister(Model model) {

		model.addAttribute("signup", new RegistrationForm());

		return "register";

	}

	@PostMapping("/register")
	public String registration(@ModelAttribute("signup") RegistrationForm form, Model model) {

		boolean status = service.userSignup(form);

		if (status) {
			model.addAttribute("succMsg", "Sign up Successfully");
		} else {
			model.addAttribute("errMsg", "erorr occured..");
		}

		model.addAttribute("signup", new RegistrationForm());

		return "register";

	}

	@GetMapping("/login")
	public String loginPage(Model model) {

		model.addAttribute("loginForm", new LoginForm());
		return "login";

	}

	@PostMapping("/login")
	public String login(@ModelAttribute("loginForm") LoginForm form, Model model) {

		boolean status = service.loginVerify(form);

		if (status) {
			return "redirect:/dashboard";
		} else {

			model.addAttribute("errMsg", "Invalid credentials");

			return "login";
		}

	}

	// search functionality

	@GetMapping("/searchblogs")

	public String search(@RequestParam("title") String title, Model model) {

		List<CreatePostDetails> searchResults = postRepo.findByTitleContaining(title);

		model.addAttribute("bloglist", searchResults);

		return "allpost";

	}

}