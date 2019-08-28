package com.codingdojo.gymbuddy.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.codingdojo.gymbuddy.models.Message;
import com.codingdojo.gymbuddy.models.Users;
import com.codingdojo.gymbuddy.services.MessageService;
import com.codingdojo.gymbuddy.services.UserService;
import com.codingdojo.gymbuddy.validator.UserValidator;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private UserValidator userValidator;
	@Autowired
	MessageService messageService;

/////////////// Index Page //////////////////////////////////////////////
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String logReg(@ModelAttribute("users") Users users, Model model) {
		return "view/index.jsp";
	}

/////////////// Registration Page //////////////////////////////////////////////
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String Reg(@ModelAttribute("users") Users users, Model model) {
		return "view/register.jsp";
	}

/////////////// Login Page //////////////////////////////////////////////
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String log(@ModelAttribute("users") Users users, Model model) {
		return "view/login.jsp";
	}

////////////////////////////Logging In ////////////////////////////////////////////////////////////	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginUser(@RequestParam("email") String email, @RequestParam("password") String password, Model model,
			HttpSession session, @ModelAttribute("users") Users users) {
		boolean isAuthenticated = userService.authenticateUser(email, password);
		if (isAuthenticated) {
			Users u = userService.findByEmail(email);
			session.setAttribute("userId", u.getId());
			return "redirect:/home";
		} else {
			model.addAttribute("users", new Users());
			model.addAttribute("error", "Invalid Credentials. Please try again.");
			return "/view/login.jsp";
		}
	}

///////////////////////// Registration //////////////////////////////////
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerUser(@Valid @ModelAttribute("users") Users users, BindingResult result, HttpSession session) {
		userValidator.validate(users, result);
		if (result.hasErrors()) {
			return "view/register.jsp";
		}
		Users u = userService.save(users);
		session.setAttribute("userId", u.getId());
		return "redirect:/home";

	}

/////////////////////// Home //////////////////////////////////
	@RequestMapping("/home")
	public String add(Model model, HttpSession session) {
		Long id = (Long) session.getAttribute("userId");
		Users users = userService.findUserById(id);
		model.addAttribute("users", users);

		return "view/home.jsp";
	}

//////////////////////////// *************** (For friend page) ////////////////////////////////////////////////////////////	
	@RequestMapping("/users/{id}")
	public String show(@PathVariable("id") Long id, Model model, HttpSession session,
			@ModelAttribute("msg") Message message) {
		Long uId = (Long) session.getAttribute("userId");
		Users users = userService.findUserById(uId);
		model.addAttribute("users", users);
		model.addAttribute("sender_id", (Long)session.getAttribute("userId"));
		model.addAttribute("messages", users.getMessagesRec());
		return "/view/dashboard.jsp";
	}
///////////////////// Send Message To Friends ////////////////////////////////////////////////
	@PostMapping("/users/{id}/addmsg")
	public String message(@PathVariable("id") Long id, @Valid @ModelAttribute("msg") Message msg, BindingResult result,
			Model model, HttpSession session) {
		Long uId = (Long) session.getAttribute("userId");
		Users users = userService.findUserById(uId);
		model.addAttribute("users", users);
		List<Message> messages = users.getMessagesRec();
		model.addAttribute("messages", messages);
		if (result.hasErrors()) {
			System.out.println(result.getAllErrors());
			model.addAttribute("error", "Invalid Credentials. Please try again.");
			return "view/dashboard.jsp";
		} else {
			msg.setReceiver(users);
			msg.setId(null);
			messageService.create(msg);
//			messageService.update(msg);
//			messages.add(msg);
//			users.setMessages(messages);
			return "redirect:/users/{id}";
		}
	}

/////////////////////////////// Logging Out //////////////////////////////////////////////////////////////
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
}
