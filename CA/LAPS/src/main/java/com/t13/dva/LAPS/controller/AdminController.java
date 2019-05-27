package com.t13.dva.LAPS.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.t13.dva.LAPS.model.User;
import com.t13.dva.LAPS.service.UserService;

@Controller
public class AdminController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/admin/home", method = RequestMethod.GET)
	public String homePage() {
		return "admin/home";
	}

	@RequestMapping(value = "/admin/userdetail", method = RequestMethod.GET)
	public String UserDetailPage (HttpServletRequest request, Model model) {
		int page = 0, size = 5;		
		if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
			page = Integer.parseInt(request.getParameter("page")) - 1;
		}		
		if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
			size = Integer.parseInt(request.getParameter("size"));
		}
		model.addAttribute("users", userService.findAllusers(PageRequest.of(page, size)));
		return "admin/userdetail";
	}
	
	@RequestMapping(value = "/admin/create", method = RequestMethod.GET)
    public String cretePage(Model model){
        model.addAttribute("user", new User());
        return "admin/create";
    }
	
	@RequestMapping(value = "/admin/create", method = RequestMethod.POST)
	public String createNewUser(@Validated User user, BindingResult bindingResult, Model model, HttpServletRequest request) {
	    User userExists = userService.findUserByUsername(user.getUsername());
	    if (userExists != null) {
	        bindingResult.rejectValue("username", "error.user", "* There is already a user registered with the username provided");
	    }
	    
	    if (bindingResult.hasErrors()) {
	        return "admin/create";
	    } else {
	    	user.setMedicalleaveday(60);
	    	if(user.getRoleid() == 1) {
	    		user.setAnnualleaveday(14);
	    	} else user.setAnnualleaveday(18);
	        userService.saveUser(user);
	        request.setAttribute("successMessage", "User has been registered successfully");
	        model.addAttribute("user", new User());
	        return "admin/create";
        }
    }
	
	@RequestMapping(path = "/admin/edit/{id}", method = RequestMethod.GET)
	public String editPage (Model model, @PathVariable(value = "id") int id) {
		User user = userService.findUserById(id);
		model.addAttribute("user", user);
		model.addAttribute("id", id);
		return "admin/edit";
	}
	
	@RequestMapping(path = "/admin/edit/{id}", method = RequestMethod.POST)
	public String editLeave (@Validated User user, BindingResult bindingResult, @PathVariable(value = "id") int id) {
		User userExists = userService.findUserByUsername(user.getUsername());
	    if (userExists != null && userExists.getId() != id) {
	        bindingResult.rejectValue("username", "error.user", "* There is already a user registered with the username provided");
	    }
		if (bindingResult.hasErrors()) {
			return "admin/edit";
		} else {
			userService.saveUser(user);
			return "redirect:/admin/userdetail";
		}
	}
	
	@RequestMapping(path = "/admin/delete/{id}", method = RequestMethod.POST)
	public String deleteUser (@PathVariable(value = "id") int id) {
		User user = userService.findUserById(id);
		userService.deleteUser(user);
		return "redirect:/admin/userdetail";
	}
	
	
}
