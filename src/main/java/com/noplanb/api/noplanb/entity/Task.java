package com.noplanb.api.noplanb.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@EntityListeners(AuditingEntityListener.class)
public class Task extends AuditEntity<String> {
	private String title;
	private String description;
	private Date completedDate;
	private Date dueDate;
	
	@ManyToOne (fetch=FetchType.LAZY)
	@JsonIgnore
	private Project project;
	
	@ManyToOne (fetch=FetchType.LAZY)
	@JsonIgnore
	private User user;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getCompletedDate() {
		return completedDate;
	}
	public void setCompletedDate(Date completedDate) {
		this.completedDate = completedDate;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}

    public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task )) return false;
        return id != null && id.equals(((Task) o).getId());
    }
    
    @Override
    public int hashCode() {
        return 52;
    }
    
	@Override
	public String toString() {
		return "Task [Title=" + title + "]";
	}
}
