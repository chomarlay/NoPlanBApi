package com.noplanb.api.noplanb.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.noplanb.api.noplanb.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
	
	// projectId, username  -
	@Query ("SELECT t from Task t join fetch t.user u WHERE t.project.id = :projectId and u.username = :username")
	public List<Task> findAllTasksByProjectIdAndUsername(@Param("projectId") Long projectId, @Param("username") String username);

	@Modifying
	@Query ("DELETE from Task t WHERE t.project.id = :projectId")
	public void deleteAllTasksByProjectId(@Param("projectId") Long projectId);
	
	@Query ("SELECT t from Task t join fetch t.user u WHERE t.project.id = :projectId and u.username = :username and t.completedDate is null")
	public List<Task> findOutstandingTasksByProjectIdAndUsername(@Param("projectId") Long projectId, @Param("username") String username);
	
	// projectId, username, title - P - for search by title
	@Query ("SELECT t from Task t join fetch t.user u WHERE t.project.id = :projectId and u.username = :username and lower(t.title) like lower(concat('%',:title,'%'))")
	public List<Task> findAllTasksByProjectIdAndUsernameAndTitle(@Param("projectId") Long projectId, @Param("username") String username, @Param("title") String title);
	
	@Query ("SELECT t from Task t join fetch t.user u WHERE t.project.id = :projectId and u.username = :username and lower(t.title) like lower(concat('%',:title,'%')) and t.completedDate is null")
	public List<Task> findOutstandingTasksByProjectIdAndUsernameAndTitle(@Param("projectId") Long projectId, @Param("username") String username, @Param("title") String title);
	
	// username, beforeDate 
	@Query ("SELECT t from Task t join fetch t.user u WHERE u.username = :username and t.dueDate < :beforeDate and t.completedDate is null")
	public List<Task> findOutstandingTasksByUsernameBeforeDate(@Param("username") String username, @Param("beforeDate") Date beforeDate );
	
	// username, title, beforeDate  -- for search by tile
	@Query ("SELECT t from Task t join fetch t.user u WHERE u.username = :username and t.dueDate < :beforeDate and lower(t.title) like lower(concat('%',:title,'%')) and t.completedDate is null")
	public List<Task> findOutstandingTasksByUsernameAndBeforeDateAndTitle(@Param("username") String username, @Param("beforeDate") Date beforeDate, @Param("title") String title );
	
}
