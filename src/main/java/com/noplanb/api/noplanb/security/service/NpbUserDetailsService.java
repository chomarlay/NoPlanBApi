package com.noplanb.api.noplanb.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.noplanb.api.noplanb.entity.User;
import com.noplanb.api.noplanb.repository.UserRepository;
import com.noplanb.api.noplanb.security.model.UserPrincipal;

@Service
public class NpbUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		User user = userRepository.findByUsername(username).orElseThrow(() -> 
//        		new UsernameNotFoundException("User not found with username  : " + username));
		User user = new User("noplanb", "noplanb@noplanb.com", "noplanb");
		return UserPrincipal.create(user);

	}
}
