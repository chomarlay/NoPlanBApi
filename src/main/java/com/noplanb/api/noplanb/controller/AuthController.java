package com.noplanb.api.noplanb.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.noplanb.api.noplanb.security.payload.LoginRequest;
import com.noplanb.api.noplanb.security.payload.SignUpRequest;



@RestController
public class AuthController {

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser (@Valid @RequestBody LoginRequest loginRequest){
		return ResponseEntity.ok("Signed in successfully");
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser (@Valid @RequestBody SignUpRequest loginRequest){
		return ResponseEntity.ok("Signed up successfully");
	}

}
