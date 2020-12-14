package com.noplanb.api.noplanb.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)

@Table(name = "user", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
            "username"
        }),
        @UniqueConstraint(columnNames = {
            "email"
        })
})

public class User extends AuditEntity<String> {
	
    @NotBlank (message = "User name is mandatory")
    @Size(max = 40)
	private String username;
    
    @NotBlank (message = "Email is mandatory")
    @Size(max = 40)
	private String email;
    
    @NotBlank (message = "Password is mandatory")
    @Size(max = 100)
	private String password;
    
    @Enumerated(EnumType.STRING)
    @Column(length = 60)
    private Role role;

    public User() {
    	
    }
    
    public User (String username, String email, String password, Role role) {
    	this.username = username;
    	this.email = email;
    	this.password = password;
    	this.role = role;
    }
    
    public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User )) return false;
        return id != null && id.equals(((User) o).getId());
    }
    
    @Override
    public int hashCode() {
        return 50;
    }	

	@Override
	public String toString() {
		return "User [Username=" + username + "]";
	}
}
