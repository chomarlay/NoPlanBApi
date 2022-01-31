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

// test without spring - junit/mockito on its own
@ExtendWith(MockitoExtension.class)
// note: the above replcaces the deprecated JUnit4 @RunWith(MockitoJUnitRunner.class).

public class NoplanbServiceTests {
	
	@InjectMocks
	private NoplanbService noplanbService;
	
	@Mock
	private ProjectRepository projectRepository;
	
	@Test
	public void getProjectTitleTest() {
    	Project project = new Project();
    	project.setTitle("ABCd");
    	User user = new User();
    	user.setUsername("kermit");
    	project.setUser(user);
    	Optional<Project> projectOptional = Optional.of(project);
        Mockito.when(projectRepository.findById(Mockito.anyLong())).thenReturn(projectOptional);
        
		Project returnProject = noplanbService.getProjectById(1L, "kermit");
		assertEquals("ABCd", returnProject.getTitle());
	}
	
	@Test
	public void invalidProjectTest() {
		Mockito.when(projectRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		assertThrows(ProjectNotFoundException.class, ()->noplanbService.getProjectById(2L, "kermit"));
	}

}
