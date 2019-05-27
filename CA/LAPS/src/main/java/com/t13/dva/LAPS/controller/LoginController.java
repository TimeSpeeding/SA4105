package com.t13.dva.LAPS.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.t13.dva.LAPS.model.User;
import com.t13.dva.LAPS.service.UserService;

@Controller
public class LoginController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(value = {"/","/login"}, method = RequestMethod.GET)
	public String login() {
		return "login";
	}
	
	@RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model){
        model.addAttribute("user", new User());
        return "registration";
    }
	
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public String createNewUser(@Valid User user, BindingResult bindingResult, Model model, HttpServletRequest request) {
	    User userExists = userService.findUserByUsername(user.getUsername());
	    if (userExists != null) {
	        bindingResult.rejectValue("username", "error.user", "* There is already a user registered with the username provided");
	    }
	    
	    if (bindingResult.hasErrors()) {
	        return "registration";
	    } else {
	        userService.saveUser(user);
	        request.setAttribute("successMessage", "User has been registered successfully");
	        model.addAttribute("user", new User());
	        return "registration";
        }
	}	
	
}
