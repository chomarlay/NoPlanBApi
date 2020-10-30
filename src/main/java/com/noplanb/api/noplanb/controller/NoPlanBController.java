package com.noplanb.api.noplanb.controller;

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
											@RequestParam(required=false) boolean all,
											@RequestParam(required=false) String title ) {
		List<Project> projects;
		if (all) {
			if (title==null) {
				projects = noPlanBService.getAllProjectsByUsername(username);			
			} else {
				projects = noPlanBService.getAllProjectsByUsernameAndTitle(username, title);
			}

		} else {
			if (title==null) {
				projects = noPlanBService.getOutstandingProjectsByUsername(username);	
			} else {
				projects = noPlanBService.getOutstandingProjectsByUsernameAndTitle(username, title);
			}
		}

		return projects;
	}
	
	@GetMapping("/projects/{id}")
	public Project retrieveProject(	@PathVariable(name="id") Long id, 
									@RequestParam(name="username") String username) {
		Project project = noPlanBService.getProjectById(id, username);
		return project;
	}
	
	@GetMapping("/projects/{id}/tasks")
	public List<Task> retrieveTasks(@PathVariable(name="id") Long id, 
									@RequestParam String username, 
									@RequestParam(required=false) boolean all,
									@RequestParam(required=false) String title) {
		List<Task> tasks;
		if (all) {
			if (title==null) {
				tasks = noPlanBService.getAllTasksByProjectIdAndUsername(id, username);
			} else {
				tasks = noPlanBService.getAllTasksByProjectIdAndUsernameAndTitle(id, username, title);
			}
		} else {
			if (title==null) {
				tasks = noPlanBService.getOutstandingTasksByProjectIdAndUsername(id, username);
			} else {
				tasks = noPlanBService.getOutstandingTasksByProjectIdAndUsernameAndTitle(id, username, title);
			}
		}

		return tasks;
	}
}
