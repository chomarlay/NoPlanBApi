package com.noplanb.api.noplanb;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.noplanb.api.noplanb.controller.NoPlanBController;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class NoplanbApplicationTests {
	
	@Test
	void contextLoads() {
	}

	@Autowired
	public NoPlanBController noplanbController;
	
	@Test
	public void testFindProduct() {
		noplanbController.retrieveProject(1L);
		assertTrue(true);
	}
}
