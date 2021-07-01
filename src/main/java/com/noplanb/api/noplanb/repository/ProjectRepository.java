package com.noplanb.api.noplanb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.noplanb.api.noplanb.entity.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>{

	
	@Query ("SELECT p from Project p WHERE lower(p.title) like lower(concat('%',:title,'%')) and p.user.username = :username")
	public List<Project> findAllByUsernameAndTitle (@Param("username") String username, @Param("title") String title);
	
	@Query ("SELECT p from Project p WHERE lower(p.title) like lower(concat('%',:title,'%')) and p.user.username = :username and p.completedDate is null")
	public List<Project> findOutstandingByUsernameAndTitle ( @Param("username") String username, @Param("title") String title);
	
	@Query ("SELECT p from Project p WHERE p.user.username = :username")
	public List<Project> findAllByUsername (@Param("username") String username);
	
	@Query ("SELECT p from Project p WHERE p.user.username = :username and p.completedDate is null")
	public List<Project> findOutstandingByUsername (@Param("username") String username);
	
}
