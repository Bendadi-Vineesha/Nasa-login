package com.cts.login.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
//import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.login.model.LoginCredentials;
import com.cts.login.service.LoginServiceImpl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.ServletException;

@RestController
@RequestMapping("/auth")
//@CrossOrigin(origins = "http://localhost:4200")
public class LoginController {

	private final LoginServiceImpl loginService;
	
	private Map<String,String> map = new HashMap<>();
    
	@Autowired
    public LoginController(LoginServiceImpl loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginCredentials userCredentials) throws Exception {
            if (userCredentials.getUsername() == null || userCredentials.getPassword() == null) {
                throw new Exception("username and password cannot be null");
            }    
            try {
    			String jwtToken = generateToken(userCredentials.getUsername(), userCredentials.getPassword());
    			 String email = loginService.findEmailByUserNameAndPassword(userCredentials.getUsername(),
     		    		userCredentials.getPassword());
    			map.put("message", "User Successfully LoggedIn");
    			
    			map.put("token", jwtToken);
    			map.put("email", email);
    			map.put("username", userCredentials.getUsername());
    		
    		
    		} catch (Exception e) {
    			map.put("message", e.getMessage());
    			map.put("token", null);
    			return new ResponseEntity<>(map,HttpStatus.UNAUTHORIZED);
    		}
			 return new ResponseEntity<>(map,HttpStatus.OK);
    		
    }

    public String generateToken(String username, String password) throws Exception {
		String jwtToken = "";
		//validate user aginst db
		boolean flag= 	loginService.findUserByUserNameAndPassword(username, password);
		if(!flag)
			throw new ServletException("Invalid Credentials");
		else {
			jwtToken = Jwts.builder()
					        .setSubject(username)
					        .setIssuedAt(new Date())
					        .setExpiration(new Date(System.currentTimeMillis() + 3000000))
					        .signWith(SignatureAlgorithm.HS256, "secret key")
					        .compact();
		}
		return jwtToken;
	}

    @GetMapping("/validateToken")
	public boolean validateToken(@RequestHeader("Authorization") String token) {
		return loginService.validateToken(token);
	}
}
