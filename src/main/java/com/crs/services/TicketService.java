package com.crs.services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crs.entity.Ticket;
import com.crs.entity.User;
import com.crs.repository.TicketRepository;
import com.crs.repository.UserRepository;

@Service
public class TicketService {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private TicketRepository trep;
	
	@Autowired
	private UserRepository urep;
	
	//CREATE METHODS
	public Ticket addTicket(Ticket ticket) {
		Optional<Ticket> top = trep.findById(ticket.getTicketId());
		if(top.isPresent()) {
			logger.warn("The ticket with Id-> {} is already present! Please contact admin for details.",ticket.getTicketId());
			return null;
		}
		else {
			trep.save(ticket);
			return trep.findById(ticket.getTicketId()).orElse(null);
		}
	}
	
	
	
	
	
	//READ METHODS
	public List<Ticket> getAllTickets(){
		return trep.findAll();
	}
	
	public Ticket getTicketWithId(String ticketId) {
		Optional<Ticket> top = trep.findById(ticketId);
		if(top.isPresent()) {
			return top.get();
		}
		else {
			logger.error("Ticket with id-> {} is not present!",ticketId);
			return null;
		}
	}
	
	public List<Ticket> getAllTicketsOfCustomer(User customer){
		return trep.findByCustomer(customer);
	}
	
	public List<Ticket> getAllTicketsOfManager(User manager){
		return trep.findByCustomer(manager);
	}
	
	public List<Ticket> getAllTicketsOfEngineer(User engineer){
		return trep.findByCustomer(engineer);
	}
	
	public List<Ticket> getAllTicketsWithStatus(String status){
		if(Ticket.isValidStatus(status)) {
			return trep.findByStatus(status);
		}
		else {
			return Collections.emptyList();
		}
	}
	
	//UPDATE METHODS
	public Ticket updateTicketCustomer(String ticketId, User customer) {
		Optional<Ticket> top = trep.findById(ticketId);
		Ticket eTicket;
		if(top.isPresent()) {
			eTicket = top.get();
			eTicket.setCustomer(customer);
			trep.save(eTicket);
			return trep.findById(ticketId).filter(tkt -> tkt.getCustomer().getUserId().equals(customer.getUserId())).orElse(null);
		}
		else {
			logger.error("ticket with id-> {} is not present!",ticketId);
			return null;
		}
	}
	
	
	
	public Ticket updateTicketManager(String ticketId, User manager) {
		Optional<Ticket> top = trep.findById(ticketId);
		Ticket eTicket;
		if(top.isPresent()) {
			eTicket = top.get();
			eTicket.setManager(manager);
			trep.save(eTicket);
			return trep.findById(ticketId).filter(tkt -> tkt.getManager().getUserId().equals(manager.getUserId())).orElse(null);
		}
		else {
			logger.error("ticket with id-> {} is not present!",ticketId);
			return null;
		}
	}
	
	public Ticket updateTicketEngineer(String ticketId, User engineer) {
		Optional<Ticket> top = trep.findById(ticketId);
		Ticket eTicket;
		if(top.isPresent()) {
			eTicket = top.get();
			
			eTicket.setEngineer(engineer);
			trep.save(eTicket);
			return trep.findById(ticketId).filter(tkt -> tkt.getEngineer().getUserId().equals(engineer.getUserId())).orElse(null);
		}
		else {
			logger.error("ticket with id-> {} is not present!",ticketId);
			return null;
		}
	}
	
	public Ticket updateTicketStatus(String ticketId,String status) {
		Optional<Ticket> top = trep.findById(ticketId);
		Ticket eTicket;
		if(top.isPresent()) {
			eTicket = top.get();
			if(Ticket.isValidStatus(status)) {
				eTicket.setStatus(status);
				trep.save(eTicket);
				return trep.findById(ticketId).filter(tkt -> tkt.getStatus().equals(status)).orElse(null);
			}
			else {
				logger.error("The status -> {} is not a valid status! Thus, ticket status update failed.",status);
				return null;
			}
		}
		else {
			logger.error("ticket with id-> {} is not present!",ticketId);
			return null;
		}
	}
	
	//DELETE METHODS
	
	public boolean deleteTicketWithId(String ticketId) {
		Optional<Ticket> top = trep.findById(ticketId);
		Ticket et;
		if(top.isPresent()) {
			et = top.get();
			trep.delete(et);
			return trep.findById(ticketId).isEmpty();
		}
		else {
			logger.error("Ticket with id ->{} is not present! Thus, deletion of the ticket failed",ticketId);
			return false;
		}
	}
}
