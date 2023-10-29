package com.crs.controllers;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
	
	
}
