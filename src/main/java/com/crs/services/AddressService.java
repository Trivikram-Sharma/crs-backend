package com.crs.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crs.entity.Address;
import com.crs.repository.AddressRepository;

@Service
public class AddressService {

	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	@Autowired
	private AddressRepository arep;
	
	//CREATE METHODS
	public boolean addAddress(Address address) {
		Optional<Address> a = arep.findById(address.getBuilding());
		if(a.isPresent()) {
			logger.warn("Address {} is already present",address);
			return false;
		}
		else {
			return arep.save(address)!=null;
		}
	}
	
	
	
	
	//READ METHODS
	public List<Address> getAllAddresses() {
		return arep.findAll();
	}
	
	public List<Address> getAddressWithPincode(String pincode) {
		return arep.findByPincode(pincode);
	}
	
	//UPDATE METHODS
	public boolean updateAddressWithId(Address address,String building) {
		Optional<Address> aop = arep.findById(building);
		if(aop.isPresent() && address.getBuilding().equals(building)) {
			return arep.save(address)!=null;
		}
		else {
			return arep.save(address)!=null;
		}
	}
	
	//DELETE METHODS
	public boolean deleteAddressWithBuilding(String building) {
		Optional<Address> a = arep.findById(building);
		if(a.isPresent()) {
			arep.delete(a.get());
			return arep.findById(building).isEmpty();
		}
		else {
			logger.warn("Address with building {} is not even present!",building);
			return true;
		}
	}
}
