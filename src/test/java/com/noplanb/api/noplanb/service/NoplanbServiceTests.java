package com.noplanb.api.noplanb.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.noplanb.api.noplanb.entity.Project;
import com.noplanb.api.noplanb.entity.User;
import com.noplanb.api.noplanb.exception.ProjectNotFoundException;
import com.noplanb.api.noplanb.repository.ProjectRepository;
import com.noplanb.api.noplanb.service.NoplanbService;

@ExtendWith(MockitoExtension.class)
//@WebMvcTest(NoplanbService.class)
public class NoplanbServiceTests {
	
	@InjectMocks
//	@Autowired
	private NoplanbService noplanbService;
	
	@Mock
//	@MockBean
	private ProjectRepository projectRepository;
	
	@Test
	public void getProjectTitleTest() {
    	Project project = new Project();
    	project.setTitle("ABCd");
    	User user = new User();
    	user.setUsername("kermit");
    	project.setUser(user);
    	Optional<Project> projectOptional = Optional.of(project);
        Mockito.when(projectRepository.findById(1L)).thenReturn(projectOptional);
        
		Project returnProject = noplanbService.getProjectById(1L, "kermit");
//		Assert.assertEquals("ABC", returnProject.getTitle());
		assertEquals("ABCd", returnProject.getTitle());
	}
	
	@Test
	public void invalidProjectTest() {
		Mockito.when(projectRepository.findById(2L)).thenThrow(ProjectNotFoundException.class);
		assertThrows(ProjectNotFoundException.class, ()->noplanbService.getProjectById(2L, "kermit"));
	}

}
