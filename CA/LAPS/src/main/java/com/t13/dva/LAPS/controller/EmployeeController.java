package com.t13.dva.LAPS.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

import com.t13.dva.LAPS.model.Compensation;
import com.t13.dva.LAPS.model.Leave;
import com.t13.dva.LAPS.model.LeaveView;
import com.t13.dva.LAPS.model.User;
import com.t13.dva.LAPS.service.CompensationService;
import com.t13.dva.LAPS.service.LeaveService;
import com.t13.dva.LAPS.service.UserService;
import com.t13.dva.LAPS.util.CalculateWorkDays;

@Controller
public class EmployeeController {
	
	@Autowired
	private LeaveService leaveService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CompensationService compensationService;


	private CalculateWorkDays calculateWorkDays = new CalculateWorkDays();
	

	private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	

	@RequestMapping(value = "/employee/home", method = RequestMethod.GET)
	public String homePage(HttpServletRequest request) {
		return "employee/home";
	}
	
	@RequestMapping(path = "/employee/create", method = RequestMethod.GET)
	public String createPage (Model model) {
		model.addAttribute("min", LocalDate.now().format(dateTimeFormatter));
		model.addAttribute("leaveView", new LeaveView());
		return "employee/create";
	}
	
	@RequestMapping(path = "/employee/create", method = RequestMethod.POST)
	public String createNewLeave (@Validated LeaveView leaveView, BindingResult bindingResult, Model model, HttpServletRequest request) {
		Leave leave = new Leave();
		if (leaveView.getStartDate() != "" && leaveView.getEndDate() != "") {
			leave.setStartDate(LocalDate.parse(leaveView.getStartDate(), dateTimeFormatter));
			leave.setEndDate(LocalDate.parse(leaveView.getEndDate(), dateTimeFormatter));
			if (leave.getStartDate().isBefore(LocalDate.now())) {
				bindingResult.rejectValue("startDate", "error.leaveView", "* No throwback is allowed!");
			}
			if (leave.getEndDate().isBefore(leave.getStartDate())) {
				bindingResult.rejectValue("endDate", "error.leaveView", "* We do not encourage time travel!");
			}
		}		
		if (bindingResult.hasErrors()) {
			return "employee/create";
		} else {
			leave.setStartDate(leave.getStartDate().plusDays(1));
			leave.setEndDate(leave.getEndDate().plusDays(1));
			leave.setCategory(leaveView.getCategory());
			leave.setContactDetail(leaveView.getContactDetail());
			leave.setReason(leaveView.getReason());
			leave.setWorkdis(leaveView.getWorkdis());
			leave.setUserid(userService.findUserByUsername(request.getUserPrincipal().getName()).getId());
			leave.setStatus("Applied");
			leaveService.saveLeave(leave);
	        request.setAttribute("successMessage", "Leave has been applied successfully");
	        model.addAttribute("leaveView", new LeaveView());
			return "employee/create";
		}
	}

	@RequestMapping(path = "/employee/leavedetail", method = RequestMethod.GET)
	public String leavePage (HttpServletRequest request, Model model) {
		int page = 0, size = 5;		
		if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
			page = Integer.parseInt(request.getParameter("page")) - 1;
		}		
		if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
			size = Integer.parseInt(request.getParameter("size"));
		}	
		int userid = userService.findUserByUsername(request.getUserPrincipal().getName()).getId();
		model.addAttribute("leaves", leaveService.findLeavesByUserid(PageRequest.of(page, size), userid));
		return "employee/leavedetail";
		
	}
	
	@RequestMapping(path = "/employee/edit/{id}", method = RequestMethod.GET)
	public String editPage (Model model, @PathVariable(value = "id") int id) {
		Leave leave = leaveService.findLeaveById(id);
		LeaveView leaveView = new LeaveView();
		leaveView.setCategory(leave.getCategory());
		leaveView.setContactDetail(leave.getContactDetail());
		leaveView.setReason(leave.getReason());
		leaveView.setStartDate(leave.getStartDate().format(dateTimeFormatter));
		leaveView.setEndDate(leave.getEndDate().format(dateTimeFormatter));
		model.addAttribute("min", LocalDate.now().format(dateTimeFormatter));
		model.addAttribute("leaveView", leaveView);
		model.addAttribute("id", id);
		return "employee/edit";
	}
	
	@RequestMapping(path = "/employee/edit/{id}", method = RequestMethod.POST)
	public String editLeave (@Validated LeaveView leaveView, BindingResult bindingResult, @PathVariable(value = "id") int id) {
		Leave leave = leaveService.findLeaveById(id);
		if (leaveView.getStartDate() != "" && leaveView.getEndDate() != "") {
			leave.setStartDate(LocalDate.parse(leaveView.getStartDate(), dateTimeFormatter).plusDays(1));
			leave.setEndDate(LocalDate.parse(leaveView.getEndDate(), dateTimeFormatter).plusDays(1));
			if (leave.getStartDate().isBefore(LocalDate.now())) {
				bindingResult.rejectValue("startDate", "error.leaveView", "* No throwback is allowed!");
			}
			if (leave.getEndDate().isBefore(leave.getStartDate())) {
				bindingResult.rejectValue("endDate", "error.leaveView", "* We do not encourage time travel!");
			}
		}
		if (bindingResult.hasErrors()) {
			return "employee/edit";
		} else {
			leave.setCategory(leaveView.getCategory());
			leave.setWorkdis(leaveView.getWorkdis());
			leave.setContactDetail(leaveView.getContactDetail());
			leave.setReason(leaveView.getReason());
			leave.setStatus("Updated");
			leaveService.saveLeave(leave);
			return "redirect:/employee/leavedetail";
		}
	}
	

	@RequestMapping(path = "/employee/delete/{id}", method = RequestMethod.POST)
	public String deleteLeave (@PathVariable(value = "id") int id) {
		Leave leave = leaveService.findLeaveById(id);
		leave.setStatus("Deleted");
		leaveService.saveLeave(leave);
		return "redirect:/employee/leavedetail";
	}
	
	@RequestMapping(path = "/employee/cancel/{id}", method = RequestMethod.POST)
	public String cancelLeave (@PathVariable(value = "id") int id, HttpServletRequest request) {
		Leave leave = leaveService.findLeaveById(id);
		User user = userService.findUserByUsername(request.getUserPrincipal().getName());
		if(leave.getCategory() == "Annual Leave") {
			user.setAnnualleaveday(user.getAnnualleaveday() + calculateWorkDays.getWorkDays(leave.getStartDate(), leave.getEndDate()));
		} else if (leave.getCategory() == "Medical Leave") {
			user.setMedicalleaveday(user.getMedicalleaveday() + calculateWorkDays.getWorkDays(leave.getStartDate(), leave.getEndDate()));
		} else if (leave.getCategory() == "Compensation Leave") {
			user.setOverworkhour(user.getOverworkhour() + 4);
		}
		userService.saveUser(user);
		leave.setStatus("Cancelled");
		leaveService.saveLeave(leave);
		return "redirect:/employee/leavedetail";
	}	

	@RequestMapping(path = "/employee/claim", method = RequestMethod.GET)
	public String createCompensation (Model model) {
		model.addAttribute("compensation", new Compensation());
		return "employee/claim";
	}
	
	@RequestMapping(path = "/employee/claim", method = RequestMethod.POST)
	public String createNewCompensation (@Validated Compensation compensation, BindingResult bindingResult, Model model, HttpServletRequest request) {
		
		if (bindingResult.hasErrors()) {
			return "employee/claim";
		} else {
			compensation.setUserid(userService.findUserByUsername(request.getUserPrincipal().getName()).getId());
			compensation.setStatus("Applied");
			compensationService.saveCompensation(compensation);
	        request.setAttribute("successMessage", "Compensation has been applied successfully");
	        model.addAttribute("compensation", new Compensation());
			return "employee/claim";
		}
	}

	@RequestMapping(path = "/employee/compensations", method = RequestMethod.GET)
	public String compensationPage (HttpServletRequest request, Model model) {
		
		int page = 0;
		int size = 5;
		
		if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
			page = Integer.parseInt(request.getParameter("page")) - 1;
		}		
		if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
			size = Integer.parseInt(request.getParameter("size"));
		}
		int userid = userService.findUserByUsername(request.getUserPrincipal().getName()).getId();
		model.addAttribute("compensations", compensationService.findCompensationsByUserid(PageRequest.of(page, size), userid));
		return "employee/compensations";

}
	
}
