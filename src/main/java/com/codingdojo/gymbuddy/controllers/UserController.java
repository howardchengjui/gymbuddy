package com.codingdojo.gymbuddy.controllers;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.codingdojo.gymbuddy.models.FileStorage;
import com.codingdojo.gymbuddy.models.Message;
import com.codingdojo.gymbuddy.models.Users;
import com.codingdojo.gymbuddy.payload.UploadFileResponse;
import com.codingdojo.gymbuddy.services.FileService;
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
	private FileService fileService;
	@Autowired
	MessageService messageService;
	
	private static final Logger logger = (Logger) LoggerFactory.getLogger(UserController.class);

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
/////////////// Add Profile Pic //////////////////////////////////////////////
@RequestMapping(value = "/addPic", method = RequestMethod.GET)
public String profilePic(@ModelAttribute("users") Users users, Model model) {
return "view/test.jsp";
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
/////////////////////////// Uploading Begins Here ///////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////// Uploading Files To App ///////////////////////////////////////////////////
	@PostMapping("/uploadFile")
	public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
		FileStorage fileName = fileService.storeFile(file);
		
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/downloadFile/")
				.path(file.getName())
				.toUriString();
		
		return new UploadFileResponse(file.getName(), fileDownloadUri,
				file.getContentType(), file.getSize());
	}
///////////////////////////// Uploading Multiple Files ///////////////////////////////
	@PostMapping("/uploadMultipleFiles")
	public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
		return Arrays.asList(files)
				.stream()
				.map(file -> uploadFile(file))
				.collect(Collectors.toList());
	}
/////////////////////////////// Downloading Files ///////////////////////////////////////////
	@GetMapping("/downloadFile/{fileName:.+}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
		//Load file as Resource
		Resource resource = (Resource) fileService.loadFileAsResource(fileName);
		
		// Try to determine file's content type
		String contentType =  null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException ex) {
			logger.info("Could not determine file type.");
		}
		
		// Fallback to the default content type if type could not be determined
		if(contentType == null) {
			contentType = "application/octet-stream";
		}
		
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
}
