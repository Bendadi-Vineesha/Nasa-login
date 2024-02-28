package com.cts.login.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cts.login.model.Login;
import com.cts.login.model.LoginCredentials;
import com.cts.login.service.LoginServiceImpl;

@ExtendWith(MockitoExtension.class)
public class LoginControllerTest {
	
	private Login login;
	private LoginCredentials logincredentials;
	String token;
	
	@Mock
	LoginServiceImpl servicemock;
	
	@InjectMocks
	public LoginController controllermock;
	
	@Mock
	PasswordEncoder encodemock;
	
	private Map<String, String> tokenMap;
	
	
	@BeforeEach
    public void setup() {
        login = new Login(1,"vineesha", "vineesha","vineesha@gmail.com");
        logincredentials = new LoginCredentials("vineesha", "vineesha");
	}
	
	@AfterEach
    void tearDown() {
    	login = null;
    }

	 	@Test
	    void givenAUserThenCallToGenerateTokenShouldReturnNotNull() throws Exception {
	      Mockito.when(servicemock.findUserByUserNameAndPassword(login.getUsername(),login.getPassword())).thenReturn(true);
	      token = controllermock.generateToken(login.getUsername(), login.getPassword());
	      assertNotNull(token);
	    }
	 	
	 	@Test
	    void givenAUserThenShouldReturnExpectedTokenInMap() throws Exception {
	 		 Mockito.when(servicemock.findUserByUserNameAndPassword(login.getUsername(),login.getPassword())).thenReturn(true);
		      token = controllermock.generateToken(login.getUsername(), login.getPassword());
		      assertThat(token.length()).isGreaterThan(20);
	    }

	 	@Test
	    void testLoginUserWithValidCredentials() throws Exception {
	       Mockito.when(servicemock.findUserByUserNameAndPassword(login.getUsername(),login.getPassword())).thenReturn(true);
	       ResponseEntity<?> response = controllermock.loginUser(logincredentials); 
	       assertEquals(HttpStatus.OK, response.getStatusCode());
	       tokenMap = (Map<String, String>) response.getBody();
	       assertNotNull(response.getBody());
	       assertNotNull(tokenMap.containsKey("token"));
	       assertNotNull(tokenMap.containsKey("message"));
	    }
	 	
	 	@Test
	    void testLoginUserWithInValidCredentials() throws Exception {
	 		Mockito.when(servicemock.findUserByUserNameAndPassword("manasa","manasa")).thenReturn(false);
	 		  ResponseEntity<?> response = controllermock.loginUser(new LoginCredentials("manasa","manasa")); 
	 		 assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
	 		 assertNotNull(response.getBody());
	 	}
}
