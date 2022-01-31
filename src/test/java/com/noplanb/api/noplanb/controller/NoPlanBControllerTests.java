package com.noplanb.api.noplanb.controller;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import static org.hamcrest.Matchers.containsString;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.noplanb.api.noplanb.entity.Project;
import com.noplanb.api.noplanb.security.JwtAuthenticationEntryPoint;
import com.noplanb.api.noplanb.security.JwtTokenProvider;
import com.noplanb.api.noplanb.security.service.NpbUserDetailsService;
import com.noplanb.api.noplanb.service.NoplanbService;


import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers=NoPlanBController.class)
//@SpringBootTest
//@AutoConfigureMockMvc
public class NoPlanBControllerTests {

	@MockBean
	private NoplanbService noplanBService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private NpbUserDetailsService userDetailsService;
	
	@MockBean
	private JwtTokenProvider jwtProvider;
	
	@MockBean
	private JwtAuthenticationEntryPoint authEntryPoint;
	
	
	@Test
	@Disabled
	public void testFindProduct() throws Exception{
		
		Project p1 = new Project();
		p1.setTitle("hello");
		Project p2 = new Project();
		p2.setTitle("world");
		List<Project> projects = new ArrayList<>();
		projects.add(p1);
		projects.add(p2);
				
		Mockito.when(noplanBService.getProjects(Mockito.anyString(), Mockito.anyString(), Mockito.anyBoolean())).thenReturn(projects);
				
        this.mockMvc.perform(MockMvcRequestBuilders.get("/projects"))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk());
//        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE));
          
	}
	
	@Test
	public void testHello() throws Exception{
					
		Mockito.when(noplanBService.getHello()).thenReturn("Helloa");
				
        this.mockMvc.perform(MockMvcRequestBuilders.get("/hello"))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(containsString("Helloa")));

          
	}
}
