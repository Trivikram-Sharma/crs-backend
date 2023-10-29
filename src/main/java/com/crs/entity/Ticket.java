package com.crs.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Ticket {

	@Id
	private String ticketId;
	
	@ManyToOne
	private User customer;
	
	@ManyToOne
	private User manager;
	
	@ManyToOne
	private User engineer;
	
	private String description;
	
	private String status;
	
	@CreationTimestamp
	private LocalDateTime createdOn;


	public String getTicketId() {
		return ticketId;
	}


	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}


	public User getCustomer() {
		return customer;
	}


	public void setCustomer(User customer) {
		this.customer = customer;
	}


	public User getManager() {
		return manager;
	}


	public void setManager(User manager) {
		this.manager = manager;
	}


	public User getEngineer() {
		return engineer;
	}


	public void setEngineer(User engineer) {
		this.engineer = engineer;
	}

	
	public String getDescription() {
		return description;
	}


	public String getStatus() {
		return status;
	}
	
	public static boolean isValidStatus(String status) {
		return status.equals("RAISED") || status.equals("WIP")
				|| status.equals("RESOLVED") || status.equals("ESCALATED");
	}

	public void setStatus(String status) {
		if(isValidStatus(status)) {
			this.status = status;			
		}
	}
	

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}


	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}
	
	
	
	
}
