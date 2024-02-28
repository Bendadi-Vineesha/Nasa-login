package com.cts.login.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cts.login.model.Login;

@Repository
public interface LoginRepository extends JpaRepository<Login, Long> {
    
	@Query("SELECT l FROM Login l where l.username=?1 and l.password=?2")
	public Optional<Login> findByUsernameAndPassword(String userName, String password);
	
	public Optional<Login> findByUsername(String username);
	


}
