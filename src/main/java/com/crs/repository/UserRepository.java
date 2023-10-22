package com.crs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crs.entity.User;

public interface UserRepository extends JpaRepository<User, String> {

}
