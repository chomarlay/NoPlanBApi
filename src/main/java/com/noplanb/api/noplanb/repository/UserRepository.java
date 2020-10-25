package com.noplanb.api.noplanb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.noplanb.api.noplanb.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	public List<User> findByUsernameContainsIgnoreCase (String username);
	public List<User> findByEmailContainsIgnoreCase (String email);

}
