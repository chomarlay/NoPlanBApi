package com.noplanb.api.noplanb.controller;

import java.net.URI;
import java.util.Collections;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.noplanb.api.noplanb.entity.Role;
import com.noplanb.api.noplanb.entity.User;
import com.noplanb.api.noplanb.security.JwtTokenProvider;
import com.noplanb.api.noplanb.security.payload.JwtAuthenticationResponse;
import com.noplanb.api.noplanb.security.payload.LoginRequest;
import com.noplanb.api.noplanb.security.payload.SignUpRequest;
import com.noplanb.api.noplanb.service.NoplanbService;

@RestController
public class AuthController {
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	NoplanbService npbService;
	
	@Autowired
	JwtTokenProvider jwtProvider;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser (@Valid @RequestBody LoginRequest loginRequest){
		
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
				);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String accessToken = jwtProvider.generateToken(authentication);
		return ResponseEntity.ok(new JwtAuthenticationResponse(accessToken));
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser (@Valid @RequestBody SignUpRequest signUpRequest){
        // Creating user's account
        User user = new User( signUpRequest.getUsername(),
                signUpRequest.getEmail(), signUpRequest.getPassword(), Role.ROLE_USER);

        
//        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User result = npbService.createUser(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/user/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body("User registered successfully");
		
	}

}
