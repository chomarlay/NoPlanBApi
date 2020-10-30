package com.noplanb.api.noplanb.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.noplanb.api.noplanb.entity.Project;
import com.noplanb.api.noplanb.entity.Task;
import com.noplanb.api.noplanb.entity.User;
import com.noplanb.api.noplanb.exception.AccessDeniedException;
import com.noplanb.api.noplanb.exception.ProjectNotFoundException;
import com.noplanb.api.noplanb.exception.TaskNotFoundException;
import com.noplanb.api.noplanb.exception.UserNotFoundException;
import com.noplanb.api.noplanb.repository.ProjectRepository;
import com.noplanb.api.noplanb.repository.TaskRepository;
import com.noplanb.api.noplanb.repository.UserRepository;

@Service
public class NoplanbService {

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public Project getProjectById(Long projectId, String username) throws ProjectNotFoundException, AccessDeniedException {
		
		Optional<Project> projectOptional = projectRepository.findById(projectId);
		
		if (!projectOptional.isPresent()) {
			throw new ProjectNotFoundException("Project not found");
		} else {
			User user = projectOptional.get().getUser();
			if (user == null || !user.getUsername().equals(username)) {
				throw new AccessDeniedException(username + " is not allowed to access the project.");
			}
		}
		return projectOptional.get();
	}
	
	public List<Project> getAllProjectsByUsernameAndTitle( String username, String title){
		List<Project> projects = projectRepository.findAllByUsernameAndTitle( username, title);
		return projects;
	}
	
	public List<Project> getOutstandingProjectsByUsernameAndTitle( String username, String title){
		List<Project> projects = projectRepository.findOutstandingByUsernameAndTitle( username, title);
		return projects;
	}
	
	public List<Project> getAllProjectsByUsername( String username){
		List<Project> projects = projectRepository.findAllByUsername(username);
		return projects;
	}
	
	public List<Project> getOutstandingProjectsByUsername( String username){
		List<Project> projects = projectRepository.findOutstandingByUsername(username);
		return projects;
	}
	
	public Project updateProject( Project project, String username) throws ProjectNotFoundException, AccessDeniedException {
		getProjectById(project.getId(), username);
		return projectRepository.save(project);
	}
	
	public void deleteProject( Long projectId, String username) throws ProjectNotFoundException, AccessDeniedException {
		Project project = getProjectById(projectId, username);
		projectRepository.delete(project);
	}
	
	public Project createProject(Project project, String username) throws UserNotFoundException {
		User user = getUserByName(username);
		project.setUser(user);
		return projectRepository.save(project);
	}
	
	public User getUserByName(String username) throws UserNotFoundException {
		List<User> users = userRepository.findByUsernameContainsIgnoreCase(username);
		if (users.isEmpty()) {
			throw new UserNotFoundException("User " + username + " does not exist.");
		}
		return users.get(0);
	}
	
	
	public Task getTaskById(Long taskId, String username) throws ProjectNotFoundException, AccessDeniedException {
		
		Optional<Task> taskOptional = taskRepository.findById(taskId);
		
		if (!taskOptional.isPresent()) {
			throw new TaskNotFoundException("Task not found");
		} else {
			User user = taskOptional.get().getUser();
			if (user == null || !user.getUsername().equals(username)) {
				throw new AccessDeniedException(username + " is not allowed to access the task.");
			}
		}
		return taskOptional.get();
	}
	
	public List<Task> getAllTasksByProjectIdAndUsername(Long projectId, String username) {
		return taskRepository.findAllTasksByProjectIdAndUsername(projectId, username);
	}
	
	public List<Task> getOutstandingTasksByProjectIdAndUsername(Long projectId, String username) {
		return taskRepository.findOutstandingTasksByProjectIdAndUsername(projectId, username);

	}
	
	public List<Task> getAllTasksByProjectIdAndUsernameAndTitle(Long projectId, String username, String title) {
		return taskRepository.findAllTasksByProjectIdAndUsernameAndTitle(projectId, username, title);
	}
		
	public List<Task>  getOutstandingTasksByProjectIdAndUsernameAndTitle (Long projectId, String username, String title) {
		return taskRepository.findOutstandingTasksByProjectIdAndUsernameAndTitle(projectId, username, title);
	}
	
	public List<Task>  getOutstandingTasksByUsernameBeforeDate(String username, Date beforeDate) {
		return taskRepository.findOutstandingTasksByUsernameBeforeDate(username, beforeDate);
	}
}
