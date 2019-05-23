package com.t13.dva.LAPS.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	@RequestMapping(value = {"/","/login"}, method = RequestMethod.GET)
	public String login() {
		System.out.println("login page");
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
	
	@RequestMapping(value = "/admin/hello", method = RequestMethod.GET)
	public String hello1() {
		System.out.println("admin hello page");
		return "admin/hello";
	}
	@RequestMapping(value = "/staff/hello", method = RequestMethod.GET)
	public String hello2() {
		System.out.println("staff hello page");
		return "staff/hello";
	}
	@RequestMapping(value = "/manager/hello", method = RequestMethod.GET)
	public String hello3() {
		System.out.println("manager hello page");
		return "manager/hello";
	}
	
	
	
}
