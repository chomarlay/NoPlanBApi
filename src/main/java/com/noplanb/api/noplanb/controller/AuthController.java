package com.noplanb.api.noplanb.controller;

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

import com.noplanb.api.noplanb.security.JwtTokenProvider;
import com.noplanb.api.noplanb.security.payload.JwtAuthenticationResponse;
import com.noplanb.api.noplanb.security.payload.LoginRequest;
import com.noplanb.api.noplanb.security.payload.SignUpRequest;



@RestController
public class AuthController {
	
	@Autowired
	AuthenticationManager authenticationManager;
	
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
	public ResponseEntity<?> registerUser (@Valid @RequestBody SignUpRequest loginRequest){
		return ResponseEntity.ok("Signed up successfully");
	}

}
