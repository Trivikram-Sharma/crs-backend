package com.crs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crs.entity.Address;

public interface AddressRepository extends JpaRepository<Address, String> {

}
