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

import com.t13.dva.LAPS.model.CommentView;
import com.t13.dva.LAPS.model.Compensation;
import com.t13.dva.LAPS.model.Leave;
import com.t13.dva.LAPS.model.User;
import com.t13.dva.LAPS.service.CompensationService;
import com.t13.dva.LAPS.service.LeaveService;
import com.t13.dva.LAPS.service.UserService;
import com.t13.dva.LAPS.util.CalculateWorkDays;

@Controller
public class ManagerController {
	
	@Autowired
	private LeaveService leaveService;
	@Autowired
	private UserService userService;
	@Autowired
	private CompensationService compensationService;

	private CalculateWorkDays calculateWorkDays = new CalculateWorkDays();

	@RequestMapping(value = "/manager/home", method = RequestMethod.GET)
	public String home() {
		return "manager/home";
	}
	
	@RequestMapping(value = "/manager/subleave", method = RequestMethod.GET)
	public String subLeavePage(HttpServletRequest request, Model model) {
		int page = 0, size = 5;		
		if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
			page = Integer.parseInt(request.getParameter("page")) - 1;
		}
		if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
			size = Integer.parseInt(request.getParameter("size"));
		}
		int managerid = userService.findUserByUsername(request.getUserPrincipal().getName()).getId();
		model.addAttribute("leaves", leaveService.findLeavesByManagerid(PageRequest.of(page, size), managerid));
		return "manager/subleave";
	}
	
	@RequestMapping(path = "/manager/approve/{id}", method = RequestMethod.POST)
	public String approveLeave (@PathVariable(value = "id") int id, HttpServletRequest request) {
		Leave leave = leaveService.findLeaveById(id);
		leave.setStatus("Approved");
		User user = userService.findUserByUsername(request.getUserPrincipal().getName());
		if(leave.getCategory() == "Annual Leave") {
			user.setAnnualleaveday(user.getAnnualleaveday() - calculateWorkDays.getWorkDays(leave.getStartDate(), leave.getEndDate()));
		} else if (leave.getCategory() == "Medical Leave") {
			user.setMedicalleaveday(user.getMedicalleaveday() - calculateWorkDays.getWorkDays(leave.getStartDate(), leave.getEndDate()));
		} else if (leave.getCategory() == "Compensation Leave") {
			user.setOverworkhour(user.getOverworkhour() - 4);
		}
		userService.saveUser(user);
		leaveService.saveLeave(leave);
		return "redirect:/manager/subleave";
	}
	
	@RequestMapping(path = "/manager/reject/{id}", method = RequestMethod.GET)
	public String rejectPage (Model model, @PathVariable(value = "id") int id) {
		model.addAttribute("commentView", new CommentView());
		model.addAttribute("id", id);
		return "manager/reject";
	}
	
	@RequestMapping(path = "/manager/reject/{id}", method = RequestMethod.POST)
	public String rejectLeave (@Validated CommentView commentView, BindingResult bindingResult, @PathVariable(value = "id") int id) {
		Leave leave = leaveService.findLeaveById(id);
		if (bindingResult.hasErrors()) {
			return "manager/reject";
		} else {
			leave.setComment(commentView.getComment());
			leave.setStatus("Rejected");
			leaveService.saveLeave(leave);
			return "redirect:/manager/subleave";
		}
	}
	
	@RequestMapping(value = "/manager/subcompensation", method = RequestMethod.GET)
	public String subComensationPage(HttpServletRequest request, Model model) {
		int page = 0, size = 5;		
		if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
			page = Integer.parseInt(request.getParameter("page")) - 1;
		}
		if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
			size = Integer.parseInt(request.getParameter("size"));
		}
		int managerid = userService.findUserByUsername(request.getUserPrincipal().getName()).getId();
		model.addAttribute("compensations", compensationService.findCompensationsByManagerid(PageRequest.of(page, size), managerid));
		return "manager/subcompensation";
	}
	
	@RequestMapping(path = "/manager/approvecom/{id}", method = RequestMethod.POST)
	public String approveCompensation (@PathVariable(value = "id") int id, HttpServletRequest request) {
		Compensation compensation = compensationService.findCompensationById(id);
		User user = userService.findUserByUsername(request.getUserPrincipal().getName());
		user.setOverworkhour(user.getOverworkhour() + compensation.getHour());
		compensation.setStatus("Approved");
		userService.saveUser(user);
		compensationService.saveCompensation(compensation);
		return "redirect:/manager/subcompensation";
	}
	
	@RequestMapping(path = "/manager/rejectcom/{id}", method = RequestMethod.POST)
	public String rejectCompensation (@PathVariable(value = "id") int id) {
		Compensation compensation = compensationService.findCompensationById(id);
		compensation.setStatus("Rejected");
		compensationService.saveCompensation(compensation);
		return "redirect:/manager/subcompensation";
	}
	
}
