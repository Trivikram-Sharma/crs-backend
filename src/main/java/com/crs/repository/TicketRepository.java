package com.crs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crs.entity.Ticket;
import com.crs.entity.User;

public interface TicketRepository extends JpaRepository<Ticket, String> {

	
	List<Ticket> findByCustomer(User customer);
	
	List<Ticket> findByManager(User manager);
	
	List<Ticket> findByEngineer(User engineer);
	
	List<Ticket> findByStatus(String status);
}
