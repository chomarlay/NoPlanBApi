package com.noplanb.api.noplanb.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.noplanb.api.noplanb.entity.Project;
import com.noplanb.api.noplanb.entity.Task;
import com.noplanb.api.noplanb.entity.User;


@DataJpaTest 
//By default, tests annotated with @DataJpaTest are transactional and roll back at the end of each test
//see data.sql for data seeding
public class NoplanbRepositoryTests {
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ProjectRepository projectRepository;
	
	@Autowired
	TaskRepository taskRepository;
	
	@Test
	public void findProjectById() {
		Optional<Project> projectOptional = projectRepository.findById(10002L);
		assertTrue(projectOptional.isPresent());
	}
	

	@Test
	public void findAllProjectsByTitleAndUsername() {
		List<Project> projects = projectRepository.findAllByUsernameAndTitle( "theodore", "run");
		assertTrue(!projects.isEmpty());
	}
	
	@Test
	public void findOutstandingProjectsByTitleAndUsername() {
		List<Project> projects = projectRepository.findOutstandingByUsernameAndTitle("theodore", "run");
		assertTrue(!projects.isEmpty());
	}
	
	public void findAllProjectsByUser() {
		List<Project> projects = projectRepository.findAllByUsername("theodore");
		assertEquals(3, projects.size());
	}
	
	public void findOutstandingProjectsByUser() {
		List<Project> projects = projectRepository.findOutstandingByUsername("theodore");
		assertEquals(2, projects.size());
	}
	
	@Test
	public void insertProject() {
		List<User> users = userRepository.findByUsernameContainsIgnoreCase("theodore");
		assertTrue(!users.isEmpty());
		
		Project project = new Project();
		project.setTitle("Android-Kotlin");
		project.setUser(users.get(0));
		projectRepository.save(project);
		assertNotNull(project.getId());
	}
	
	@Test 
	public void deleteProject() {
		Project project = new Project();
		project.setTitle("Android");
		projectRepository.save(project);
		assertNotNull(project.getId());
		
		projectRepository.delete(project);
		
		Optional<Project> optional = projectRepository.findById(project.getId());
		assertTrue(!optional.isPresent());
	}
	
	@Test
	public void updateProjectCompletedDate() {
		Project project = new Project();
		project.setTitle("Android");
		projectRepository.save(project);
		assertNotNull(project.getId());
		
		project.setCompletedDate(new Date());
		projectRepository.save(project);
		Optional<Project> updatedOptional = projectRepository.findById(project.getId());
		assertNotNull(updatedOptional.get().getCompletedDate());
		
	}
	
	//Tasks
	@Test
	public void findAllTasksByProjectIdAndUsername() {
		List<Task> tasks = taskRepository.findAllTasksByProjectIdAndUsername(10002L, "theodore");
		assertEquals(3, tasks.size());
	}
	
	@Test
	public void findOutstandingTasksByProjectIdAndUsername() {
		List<Task> tasks = taskRepository.findOutstandingTasksByProjectIdAndUsername(10002L, "theodore");
		assertEquals(2, tasks.size());
	}
	
	@Test
	public void findAllTasksByProjectIdAndUsernameAndTitle() {
		List<Task> tasks = taskRepository.findAllTasksByProjectIdAndUsernameAndTitle(10002L, "theodore", "rUn");
		assertEquals(3, tasks.size());
	}
	
	@Test
	public void findOutstandingTasksByProjectIdAndUsernameAndTitle() {
		List<Task> tasks = taskRepository.findOutstandingTasksByProjectIdAndUsernameAndTitle(10002L, "theodore", "rUn");
		assertEquals(2, tasks.size());
	}
	
	
	@Test
	public void findOutstandingTasksByUsernameBeforeDate() {

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 1);
		Date beforeDate = cal.getTime();
		List<Task> tasks = taskRepository.findOutstandingTasksByUsernameBeforeDate("theodore", beforeDate);
		assertEquals(2, tasks.size());
	}
	
	@Test
	public void findOutstandingTasksByUsernameAndBeforeDateAndTitle() {

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 1);
		Date beforeDate = cal.getTime();
		List<Task> tasks = taskRepository.findOutstandingTasksByUsernameAndBeforeDateAndTitle("theodore",  beforeDate, "rUn");
		assertEquals(2, tasks.size());
	}
	
	@Test
	public void insertTask() {
		Optional<Project> projectOptional = projectRepository.findById(10001L);
		assertTrue(projectOptional.isPresent());
		Project project = projectOptional.get();
		Task task = new Task();
		task.setTitle("Task1");
		task.setUser(project.getUser());
		task.setProject(project);
		
		taskRepository.save(task);
		
		assertNotNull(task.getId());
	}
	
	@Test
	public void updateTaskCompletedDate() {
		Project project = new Project();
		project.setTitle("Android");
		projectRepository.save(project);
		assertNotNull(project.getId());
		
		Task task = new Task();
		task.setTitle("Task1");
		task.setUser(project.getUser());
		task.setProject(project);
		
		taskRepository.save(task);
		
		assertNotNull(task.getId());
		
		task.setCompletedDate(new Date());
		
		Optional<Task> optional = taskRepository.findById(task.getId());
		
		assertNotNull(optional.get().getCompletedDate());
	}
	
//	@Test 
	public void deleteTask() {
		Project project = new Project();
		project.setTitle("Android");
		projectRepository.save(project);
		assertNotNull(project.getId());
		
		Task task = new Task();
		task.setTitle("Task1");
		task.setProject(project);
		
		taskRepository.save(task);
		
		assertNotNull(task.getId());
		
		taskRepository.delete(task);
		
		Optional<Task> optional = taskRepository.findById(task.getId());
		assertTrue(!optional.isPresent());
	}
	
}
