package com.noplanb.api.noplanb.entity;


import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Project extends AuditEntity<String> {
	
	private String title;
	private String description;
	
	@Temporal(TemporalType.DATE)
	private Date completedDate;
	
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
		
    public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Project )) return false;
        return id != null && id.equals(((Project) o).getId());
    }
    
    @Override
    public int hashCode() {
        return 51;
    }	

	@Override
	public String toString() {
		return "Project [Title=" + title + "]";
	}
}
