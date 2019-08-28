package com.codingdojo.gymbudy.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.codingdojo.gymbudy.models.User;
import com.codingdojo.gymbudy.services.UserService;
import com.codingdojo.gymbudy.validator.UserValidator;

@Controller
public class UserController {
	@Autowired 
	UserService userService;
	@Autowired
	UserValidator userValidator;

	@RequestMapping("/registration")
    public String registerForm(@ModelAttribute("user") User user) {
        return "tvshow/loginRegistrationPage.jsp";
    }
    @GetMapping("/login")
    public String login(@ModelAttribute("user") User user) {
        return "tvshow/loginRegistrationPage.jsp";
    }
    
    @PostMapping("/registration")
    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result, HttpSession session,RedirectAttributes errors) {
    	userValidator.validate(user, result);
    	if(result.hasErrors()) {
    		errors.addFlashAttribute("errors","Invalid Credentials. Please try again.");
    		return "redirect:/registration";
    	}else {
    		User u = userService.registerUser(user);
    		session.setAttribute("user",u);
    		return "redirect:/shows";
    	}
        // if result has errors, return the registration page (don't worry about validations just now)
        // else, save the user in the database, save the user id in session, and redirect them to the /home route
    }
    
    @PostMapping("/login")
    public String loginUser(@RequestParam("email") String email, @RequestParam("password") String password, Model model, HttpSession session,RedirectAttributes errors) {
        boolean isAuthenticated = userService.authenticateUser(email,password);
    	if(isAuthenticated) {
    		User u = userService.findByEmail(email);
    		session.setAttribute("user",u);
    		return "redirect:/shows";
    	}else{
//    		model.addAttribute("user",new User());
    		errors.addFlashAttribute("errors","Invalid Credentials. Please try again.");
    		return "redirect:/login";
    	}

    	// if the user is authenticated, save their user id in session
        // else, add error messages and return the login page
    }
    
   
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login";

        // invalidate session
        // redirect to login page
    }
/////////////////////////////event///////////////////////////
    
}


