package com.crs.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crs.entity.Ticket;
import com.crs.entity.User;
import com.crs.services.TicketService;
import com.crs.services.UserService;

@RestController
@RequestMapping("/api/ticket")
public class TicketController {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private TicketService tservice;
	
	@Autowired
	private UserService uservice;

	@PostMapping("/add")
	public Ticket addTicket(@RequestBody Ticket ticket) {
		return tservice.addTicket(ticket);
	}
	
	@PutMapping("/assign/engineer")
	public Ticket assignTicketToEngineer(@RequestParam(required = true) String ticketId, @RequestBody User engineer) {
		Ticket ticket = tservice.getTicketWithId(ticketId);
		User eop = uservice.getUserWithId(engineer.getUserId());
		if(ticket!=null && ticket.getCustomer()!=null && ticket.getManager()!=null) {
			if(eop!=null) {
			return tservice.updateTicketEngineer(ticketId, engineer);}
			else {
				return null;
			}
		}
		else {
			logger.error("Ticket Not Assignable to engineer, as it is either not present, or it doesn't have customer/manager.");
			return null;
		}
	}
	
	@GetMapping("/get/all")
	public List<Ticket> getAllTickets(@RequestParam(required = true) String userId){
		User user = uservice.getUserWithId(userId);
		if(user==null || !user.getUserType().equals("admin")) {
			return null;
		}
		else {
			return tservice.getAllTickets();
		}
	}
	
	@GetMapping("/get/id")
	public Ticket getTicketWithId(@RequestParam(required = true) String ticketId) {
		return tservice.getTicketWithId(ticketId);
	}
	
	@GetMapping("/get/customer")
	public List<Ticket> getAllTicketsOfCustomer(@RequestParam(required = true) String userId){
		User user = uservice.getUserWithId(userId);
		if(user==null || !user.getUserType().equals("customer")) {
			return null;
		}else {
			return tservice.getAllTicketsOfCustomer(user);
		}
	}
	
	@GetMapping("/get/manager")
	public List<Ticket> getAllTicketsOfManager(@RequestParam(required = true) String userId) {
		User user = uservice.getUserWithId(userId);
		if(user==null || !user.getUserType().equals("manager")) {
			return null;
		}else {
			return tservice.getAllTicketsOfManager(user);
		}
	}
	
	@GetMapping("/get/engineer")
	public List<Ticket> getAllTicketsOfEngineer(@RequestParam(required = true) String userId){
		User user = uservice.getUserWithId(userId);
		if(user==null || !user.getUserType().equals("engineer")) {
			return null;
		}else {
			return tservice.getAllTicketsOfEngineer(user);
		}
	}
	
	@GetMapping("/get/status")
	public List<Ticket> getAllTicketsWithStatus(@RequestParam(required = true) String status) {
		if(Ticket.isValidStatus(status)) {
			return tservice.getAllTicketsWithStatus(status);
		}
		else {
			return null;
		}
	}
	
	@PatchMapping("/update/manager")
	public Ticket updateTicketManager(@RequestParam(required = true) String userId,@RequestBody Ticket ticket) {
		User user = uservice.getUserWithId(userId);
		if(user ==null || !user.getUserType().equals("manager")) {
			return null;
		}
		else {
			return tservice.updateTicketManager(ticket.getTicketId(), user);
		}
	} 
	
}
