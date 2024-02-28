package com.cts.login.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cts.login.exception.UserNotFoundException;
import com.cts.login.model.Login;
import com.cts.login.repository.LoginRepository;

import io.jsonwebtoken.Jwts;

@Service
public class LoginServiceImpl {

	@Autowired
	LoginRepository loginrepo;

	public boolean findUserByUserNameAndPassword(String userName, String password) throws Exception {
		Optional<Login> isUserAvailable = loginrepo.findByUsername(userName);
		BCryptPasswordEncoder decode = new BCryptPasswordEncoder();
		Login login = isUserAvailable.get();
		if (!(decode.matches(password, login.getPassword()))) {
			throw new Exception("Invalid Password");
		}
		if (!isUserAvailable.isPresent()) {
			throw new UserNotFoundException("could not find valid User with this username" + userName + "and password");
		} else {
			return true;
		}
	}

	public Login findUserByUserName(String username) throws UserNotFoundException {
		Optional<Login> isUserAvailable = loginrepo.findByUsername(username);
		if (!isUserAvailable.isPresent()) {
			throw new UserNotFoundException("could not find valid User with this username" + username);
		} else {
			return isUserAvailable.get();
		}

	}

	public Login saveUserDetails(Login logindetails) {
		return loginrepo.save(logindetails);
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey("secret key").parseClaimsJws(token).getBody();
			return true;
		} catch (Exception e) {

			return false;
		}
	}
	
	public String findEmailByUserNameAndPassword(String username, String password) throws Exception {
		Optional<Login> isUserAvailable = loginrepo.findByUsername(username);
		BCryptPasswordEncoder decode = new BCryptPasswordEncoder();
		if (isUserAvailable.isPresent()) {
			Login details = isUserAvailable.get();
			if (!(decode.matches(password, details.getPassword()))) {
				throw new Exception("Invalid Password");
			}
			return details.getEmail();
		} else {
			throw new UserNotFoundException("could not find valid User with this username" + username);
		}
	}

}