package com.noplanb.api.noplanb.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.noplanb.api.noplanb.entity.Project;
import com.noplanb.api.noplanb.entity.Task;
import com.noplanb.api.noplanb.entity.User;
import com.noplanb.api.noplanb.exception.AccessDeniedException;
import com.noplanb.api.noplanb.exception.ProjectNotFoundException;
import com.noplanb.api.noplanb.exception.SignupException;
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
	
	@Transactional
	public void deleteProjectById(Long projectId, String username) throws ProjectNotFoundException, AccessDeniedException {
		
		Project project = getProjectById(projectId, username);
		// delete tasks for the project.  Note: Avoiding delete cascade from project entity with one to many relationship
		taskRepository.deleteAllTasksByProjectId(projectId);
		projectRepository.delete(project);
	}
	
	public List<Project> getProjects( String username, String title, boolean all) {
		if (all) {
			return getAllProjects(username, title);
		} else {
			return getOutstandingProjects(username, title);
		}
	}
	
	private List<Project> getAllProjects( String username, String title){
		List<Project> projects;
		if (title == null) {
			projects = projectRepository.findAllByUsername(username);
		} else  {
			projects = projectRepository.findAllByUsernameAndTitle(username, title);
		}
		return projects;
	}
	
	private List<Project> getOutstandingProjects( String username, String title){
		List<Project> projects;
		if (title == null) {
			projects = projectRepository.findOutstandingByUsername(username);
		} else {
			projects = projectRepository.findOutstandingByUsernameAndTitle( username, title);
		}
		return projects;
	}
	
	public Project updateProject( Project project, String username) throws ProjectNotFoundException, AccessDeniedException {
		Project existingProject = getProjectById(project.getId(), username);
		existingProject.setTitle(project.getTitle());
		existingProject.setDescription(project.getDescription());
		existingProject.setCompletedDate(project.getCompletedDate());
		
		return projectRepository.save(existingProject);
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
	
	public User getUserById(Long id) throws UserNotFoundException {
		Optional<User> userOpt = userRepository.findById(id);
		if (!userOpt.isPresent()) {
			throw new UserNotFoundException("User Id: " + id + " does not exist.");
		}
		return userOpt.get();
	}
	
	public User createUser(User user) throws SignupException  {
    	if (userRepository.existsByUsername(user.getUsername())) {
    		throw new SignupException("User name already exists");
    	}
    	if (userRepository.existsByEmail(user.getEmail())) {
    		throw new SignupException("Email address already exists");
    	}
		return userRepository.save(user);
	}
	
	
	public Task createTask(Long projectId, Task task, String username) throws ProjectNotFoundException, UserNotFoundException {
		Project project = getProjectById(projectId, username);
		User user = getUserByName(username);
		task.setProject(project);
		task.setUser(user);
		return taskRepository.save(task);
	}
	
	public Task updateTask (Task task, String username) throws ProjectNotFoundException, UserNotFoundException {
		Task existingTask = getTaskById(task.getId(), username);
		existingTask.setTitle(task.getTitle());
		existingTask.setDescription(task.getDescription());
		existingTask.setCompletedDate(task.getCompletedDate());
		existingTask.setDueDate(task.getDueDate());
			
		return taskRepository.save(existingTask);
	}
	
	public Task getTaskById(Long taskId, String username) throws TaskNotFoundException, AccessDeniedException {
		
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
	
	public void deleteTaskById(Long taskId, String username) throws TaskNotFoundException, AccessDeniedException {
		
		Task task = getTaskById(taskId, username);
		taskRepository.delete(task);
	}
	
	public List<Task> getTasksByProjectId(Long projectId, String username, String title, boolean all) {
		if (all) {
			return getAllTasks(projectId, username, title);
		} else {
			return getOutstandingTasks(projectId, username, title);
		}
	}
	

	private List<Task> getAllTasks(Long projectId, String username, String title) {
		if (title == null) {
			return taskRepository.findAllTasksByProjectIdAndUsername(projectId, username);
		} else {
			return taskRepository.findAllTasksByProjectIdAndUsernameAndTitle(projectId, username, title);
		}
	}
	
	private List<Task> getOutstandingTasks(Long projectId, String username, String title) {
		if (title == null) {
			return taskRepository.findOutstandingTasksByProjectIdAndUsername(projectId, username);
		} else {
			return taskRepository.findOutstandingTasksByProjectIdAndUsernameAndTitle(projectId, username, title);
		}
	}
	
	public List<Task> getTasksBeforeDate(String username, Date date, String title) {
		if (title == null) {
			return taskRepository.findOutstandingTasksByUsernameBeforeDate( username, date);
		} else {
			return taskRepository.findOutstandingTasksByUsernameAndBeforeDateAndTitle ( username, date, title);
		}
	}
	
}
