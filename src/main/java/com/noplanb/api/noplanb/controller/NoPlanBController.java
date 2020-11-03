package com.noplanb.api.noplanb.controller;

import java.net.URI;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.noplanb.api.noplanb.entity.Project;
import com.noplanb.api.noplanb.entity.Task;
import com.noplanb.api.noplanb.service.NoplanbService;

@RestController
public class NoPlanBController {

	@Autowired
	NoplanbService noPlanBService;
	

	@GetMapping("/projects")
	public List<Project> retrieveProjects(	@RequestParam String username,
											@RequestParam(required=false) String title,
											@RequestParam(required=false) boolean all) {
		return  noPlanBService.getProjects(username, title, all);
	}
	
	@GetMapping("/projects/{id}")
	public Project retrieveProject(	@PathVariable(name="id") Long projectId, 
									@RequestParam(name="username") String username) {
		
		return noPlanBService.getProjectById(projectId, username);
	}
	
	@DeleteMapping("/projects/{id}")
	public void deleteProject(	@PathVariable(name="id") Long projectId, 
									@RequestParam(name="username") String username) {
		
		noPlanBService.deleteProjectById(projectId, username);
	}
	
	@PostMapping("/projects")
	public ResponseEntity<Object> createProject(@Valid @RequestBody Project project,
												@RequestParam(name="username") String username) {
		Project p = noPlanBService.createProject(project, username);
		// return reponse with CREATED status and  header with id as a url
		// /projects/{id}
		// note: no body returned

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(p.getId()).toUri();

		return ResponseEntity.created(location).build();
	}
	
	
	@GetMapping("/projects/{id}/tasks")
	public List<Task> retrieveTasks(@PathVariable(name="id") Long projectId, 
									@RequestParam String username,
									@RequestParam(required=false) String title,
									@RequestParam(required=false) boolean all) {
		
		return noPlanBService.getTasksByProjectId(projectId, username, title, all);

	}
	
	@PostMapping("/projects/{id}/tasks")
	public ResponseEntity<Object> createTask(@PathVariable(name="id") Long projectId,
									@Valid @RequestBody Task task,
									@RequestParam String username) {
		
		Task t = noPlanBService.createTask(projectId, task, username);
		// return reponse with CREATED status and  header with id as a url
		// /projects/{id}
		// note: no body returned

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(t.getId()).toUri();

		return ResponseEntity.created(location).build();

	}
	
	@GetMapping("/tasks")
	public List<Task> retrieveTasks(	@RequestParam String username,
											@RequestParam int dueindays,
											@RequestParam(required=false) String title) {
		
		LocalDate localDate = LocalDate.now().plusDays(dueindays+1);
		Date beforeDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

		return  noPlanBService.getTasksBeforeDate(username,beforeDate,title);
	}
	
	@GetMapping("/tasks/{id}")
	public Task retrieveTask(	@PathVariable(name="id") Long taskId, 
									@RequestParam(name="username") String username) {
		
		return noPlanBService.getTaskById(taskId, username);
	}
	
	@DeleteMapping("/tasks/{id}")
	public void deleteTask(	@PathVariable(name="id") Long taskId, 
									@RequestParam(name="username") String username) {
		
		noPlanBService.deleteTaskById(taskId, username);
	}
}
