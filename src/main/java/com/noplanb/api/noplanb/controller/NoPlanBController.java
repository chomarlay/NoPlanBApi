package com.noplanb.api.noplanb.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	
	@GetMapping("/projects/{id}/tasks")
	public List<Task> retrieveTasks(@PathVariable(name="id") Long projectId, 
									@RequestParam String username,
									@RequestParam(required=false) String title,
									@RequestParam(required=false) boolean all) {
		
		return noPlanBService.getTasksByProjectId(projectId, username, title, all);

	}
	
	@GetMapping("/tasks")
	public List<Task> retrieveTasks(	@RequestParam String username,
											@RequestParam int dueindays,
											@RequestParam(required=false) String title) {
		
		LocalDate localDate = LocalDate.now().plusDays(dueindays+1);
		Date beforeDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

		return  noPlanBService.getTasksBeforeDate(username,beforeDate,title);
	}
}
