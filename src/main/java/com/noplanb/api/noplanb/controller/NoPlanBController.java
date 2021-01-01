package com.noplanb.api.noplanb.controller;

import java.net.URI;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.noplanb.api.noplanb.entity.Project;
import com.noplanb.api.noplanb.entity.Task;
import com.noplanb.api.noplanb.security.model.UserPrincipal;
import com.noplanb.api.noplanb.service.NoplanbService;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
public class NoPlanBController {

	@Autowired
	NoplanbService noPlanBService;
	

	private UserPrincipal getUserPrincipal() {
		return (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();	
	}
	
	@GetMapping("/projects")
	public List<Project> retrieveProjects( @RequestParam(required=false) String title,
											@RequestParam(required=false) boolean all) {
		return  noPlanBService.getProjects(getUserPrincipal().getUsername(), title, all);
	}
	
	@GetMapping("/projects/{id}")
	public Project retrieveProject(	@PathVariable(name="id") Long projectId) {
		;
		return noPlanBService.getProjectById(projectId, getUserPrincipal().getUsername());
	}
	
	@DeleteMapping("/projects/{id}")
	public void deleteProject(	@PathVariable(name="id") Long projectId) {
		
		noPlanBService.deleteProjectById(projectId, getUserPrincipal().getUsername());
	}
	
	@PostMapping("/projects")
	public ResponseEntity<Object> createProject(@Valid @RequestBody Project project) {
		Project p = noPlanBService.createProject(project, getUserPrincipal().getUsername());
		// return reponse with CREATED status and  header with id as a url
		// /projects/{id}
		// note: no body returned

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(p.getId()).toUri();

		return ResponseEntity.created(location).build();
	}
	
	@PutMapping("/projects")
	public ResponseEntity<Object> updateProject(@Valid @RequestBody Project project) {
		noPlanBService.updateProject(project, getUserPrincipal().getUsername());

		return ResponseEntity.noContent().build();
	}
	
	
	@GetMapping("/projects/{id}/tasks")
	public List<Task> retrieveTasks(@PathVariable(name="id") Long projectId,
									@RequestParam(required=false) String title,
									@RequestParam(required=false) boolean all) {
		
		return noPlanBService.getTasksByProjectId(projectId, getUserPrincipal().getUsername(), title, all);

	}
	
	@PostMapping("/projects/{id}/tasks")
	public ResponseEntity<Object> createTask(@PathVariable(name="id") Long projectId,
									@Valid @RequestBody Task task) {
		
		Task t = noPlanBService.createTask(projectId, task, getUserPrincipal().getUsername());
		// return reponse with CREATED status and  header with id as a url
		// /projects/{id}
		// note: no body returned

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(t.getId()).toUri();

		return ResponseEntity.created(location).build();

	}
	
	@GetMapping("/tasks")
	public List<Task> retrieveTasks(@RequestParam int dueindays,
									@RequestParam(required=false) String title) {
		
		LocalDate localDate = LocalDate.now().plusDays(dueindays+1);
		Date beforeDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

		return  noPlanBService.getTasksBeforeDate(getUserPrincipal().getUsername(),beforeDate,title);
	}
	
	@GetMapping("/tasks/{id}")
	public Task retrieveTask(	@PathVariable(name="id") Long taskId) {
		
		return noPlanBService.getTaskById(taskId, getUserPrincipal().getUsername());
	}
	
	@DeleteMapping("/tasks/{id}")
	public void deleteTask(	@PathVariable(name="id") Long taskId) {
		
		noPlanBService.deleteTaskById(taskId, getUserPrincipal().getUsername());
	}
	
	@PutMapping("/tasks")
	public ResponseEntity<Object> updateTask(@Valid @RequestBody Task task) {
		noPlanBService.updateTask( task, getUserPrincipal().getUsername());
		return ResponseEntity.noContent().build();
	}
}
