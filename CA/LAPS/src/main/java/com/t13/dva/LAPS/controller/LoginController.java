package com.t13.dva.LAPS.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.t13.dva.LAPS.model.User;
import com.t13.dva.LAPS.service.UserService;

@Controller
public class LoginController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(value = {"/", "/login"})
	public String login() {
		return "login";
	}
	
	@RequestMapping(value= "/registration", method = RequestMethod.GET)
    public ModelAndView registration(){
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registration");
        return modelAndView;
    }
	
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
	    ModelAndView modelAndView = new ModelAndView();
	    User userExists = userService.findUserByUsername(user.getUsername());
	    if (userExists != null) {
	        bindingResult
	                .rejectValue("username", "error.user",
	                        "There is already a user registered with the username provided");
	    }
	    
	    if (bindingResult.hasErrors()) {
	        modelAndView.setViewName("registration");
	    } else {
	        userService.saveUser(user);
	        modelAndView.addObject("successMessage", "User has been registered successfully");
	        modelAndView.addObject("user", new User());
	        modelAndView.setViewName("registration");
        }
        return modelAndView;
    }
	
	@RequestMapping(value = "/hello")
	public String hello() {
		return "hello";
	}
	
//	@RequestMapping(value="/admin/home", method = RequestMethod.GET)
//    public ModelAndView home(){
//        ModelAndView modelAndView = new ModelAndView();
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        User user = userService.findUserByUsername(auth.getName());
//        modelAndView.addObject("userName", "Welcome " + user.getName()  + " (" + user.getEmail() + ")");
//        modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
//        modelAndView.setViewName("admin/home");
//        return modelAndView;
//    }
	
	
}
