package com.crs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crs.entity.Address;

public interface AddressRepository extends JpaRepository<Address, String> {

	
	public List<Address> findByPincode(String pincode);
	
}
