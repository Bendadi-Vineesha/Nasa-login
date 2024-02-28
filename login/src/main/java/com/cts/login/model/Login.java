package com.cts.login.model;

import com.fasterxml.jackson.annotation.JsonAlias;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Login {
	
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private long id;
	    private String username;
	    private String password;
	    private String email;

	    public Login() {
	    }

	    public Login(long id, String username, String password, String email) {
	        this.id = id;
	        this.username = username;
	        this.password = password;
	        this.email = email;
	    }

	    public Login(long id, String username, String password){
	        this.id = id;
	        this.username = username;
	        this.password = password;
	    }

	    //getters and setters
	    public long getId() {
	        return id;
	    }

	    public void setId(long id) {
	        this.id = id;
	    }

	    public String getUsername() {
	        return username;
	    }

	    public void setUsername(String username) {
	        this.username = username;
	    }

	    public String getPassword() {
	        return password;
	    }

	    public void setPassword(String password) {
	        this.password = password;
	    }

	    public String getEmail() {
	        return email;
	    }

	    public void setEmail(String email) {
	        this.email = email;
	    }


}
