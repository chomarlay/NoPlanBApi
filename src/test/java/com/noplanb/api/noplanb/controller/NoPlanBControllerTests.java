package com.noplanb.api.noplanb.controller;



import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;

@WebMvcTest(NoPlanBController.class)
//@SpringBootTest
public class NoPlanBControllerTests {

	@Autowired
	private NoPlanBController noplanbController;
	
	@Test
	void contextLoads() {
	}
	
	@Test
	void testFindProduct() {
		noplanbController.retrieveProject(1L);
		assertTrue(true);
	}
}
