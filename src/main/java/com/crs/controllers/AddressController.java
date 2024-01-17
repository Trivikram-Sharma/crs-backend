package com.crs.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crs.entity.Address;
import com.crs.services.AddressService;

@RestController
@RequestMapping("/api/address")
public class AddressController {

	@Autowired
	AddressService aservice;
	
	@PostMapping("/add")
	public boolean addAddress(@RequestBody(required = true) Address address) {
		return aservice.addAddress(address); 
	}
	
	@GetMapping("/get")
	public Address getAddress(@RequestParam(required = true) String id) {
		return aservice.getAddressWithBuilding(id);
	}
	
	@GetMapping("/get/all")
	public List<Address> getAllAddresses(){
		return aservice.getAllAddresses();
	}
	
	@GetMapping("/get/pincode")
	public List<Address> getAddressesWithPinCode(@RequestParam(required = true) String pincode){
		return aservice.getAddressWithPincode(pincode);
	}
	
	@PutMapping("/update")
	public Address updateAddress(@RequestBody(required = true) Address address) {
		boolean result = aservice.updateAddressWithId(address, address.getBuilding());
		return result?address:null;
	}
	
	@DeleteMapping("/delete")
	public boolean deleteAddress(@RequestParam(required = true) String building) {
		return aservice.deleteAddressWithBuilding(building);
	}
}
