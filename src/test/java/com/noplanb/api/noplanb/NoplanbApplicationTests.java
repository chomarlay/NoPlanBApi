package com.noplanb.api.noplanb;

import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.noplanb.api.noplanb.controller.NoPlanBController;
import com.noplanb.api.noplanb.entity.Project;


@ExtendWith(SpringExtension.class)
//@SpringBootTest
class NoplanbApplicationTests {
	
	@Test
	void contextLoads() {
	}

	@MockBean
	public NoPlanBController noplanbController;
	
	@Test
	public void testFindProduct() {
		Project mockProj = new Project();
		mockProj.setTitle("Project1");
		Mockito.when(noplanbController.retrieveProject(Mockito.anyLong())).thenReturn(mockProj);
		Project p = noplanbController.retrieveProject(1L);
		assertEquals(mockProj.getTitle(),p.getTitle());
	}
}
