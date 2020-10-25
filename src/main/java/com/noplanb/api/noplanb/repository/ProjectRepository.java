package com.noplanb.api.noplanb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.noplanb.api.noplanb.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Long>{

	
	@Query ("SELECT p from Project p WHERE lower(p.title) like lower(concat('%',:title,'%')) and p.user.username = :username")
	public List<Project> findByTitleAndUsername (@Param("title") String title, @Param("username") String username);
	
	@Query ("SELECT p from Project p WHERE p.user.username = :username")
	public List<Project> findAllByUsername (@Param("username") String username);
	
	@Query ("SELECT p from Project p WHERE p.user.username = :username and p.completedDate is null")
	public List<Project> findOutstandingProjectsByUsername (@Param("username") String username);
	
//	lower(u.name) like lower(concat('%', :nameToFind,'%'))
}
