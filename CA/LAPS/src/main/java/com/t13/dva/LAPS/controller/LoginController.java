package com.t13.dva.LAPS.controller;

import java.io.Console;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.t13.dva.LAPS.model.User;
import com.t13.dva.LAPS.service.UserService;

@Controller
public class LoginController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(value = {"/login"}, method = RequestMethod.GET)
	public String login() {
		System.out.println("login page");
		return "login";
	}
	
//	@RequestMapping(value = {"/login"}, method = RequestMethod.POST)
//	public String emmm(@RequestParam String username, @RequestParam String password, Model model, HttpServletRequest request) {
//		int s = userService.checkpassword(username, password);
//		System.out.println(s);
//		if (s == 1) {
//			request.getSession().setAttribute("name", username);
//			return "/hello";
//		} else if (s == 2) {
//			request.getSession().setAttribute("name", username);
//			return "/hello";
//		} else if (s == 3) {
//			request.getSession().setAttribute("name", username);
//			return "/staff/home";
//		} else {
//			model.addAttribute("error", "enter right username/password");
//			return "index";
//		}
//		
//	}
	
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
