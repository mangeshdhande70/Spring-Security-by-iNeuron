package in.ineuron.nitin.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import in.ineuron.nitin.model.UserDetails;
import in.ineuron.nitin.service.IUserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private IUserService service;

	@GetMapping("/register") //for loading the page
	public String showRegistrationPage(@ModelAttribute("userInfo") UserDetails details) {
		System.out.println("UserController.showRegistrationPage()");
		return "user_register";
	}

	@PostMapping("/register")// for submitting the page
	public String registerUser(@ModelAttribute("userInfo") UserDetails details, Map<String, Object> map) {
		System.out.println("UserController.registerUser()");
		String resultMsg = service.regsiter(details);
		map.put("message", resultMsg);
		return "user_registered_success";
	}
	
	@RequestMapping("/showLogin")
	public String showLoginPage() {
		return "custom_login";
	}

}
