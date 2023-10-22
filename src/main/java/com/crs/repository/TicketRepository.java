package com.crs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crs.entity.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, String> {

}
