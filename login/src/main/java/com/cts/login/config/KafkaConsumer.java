package com.cts.login.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.cts.login.model.Login;
import com.cts.login.service.LoginServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

//import lombok.extern.slf4j.Slf4j;

//@Slf4j
@Component
public class KafkaConsumer {

	private Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

	private static final String orderTopic = "${topic.name}";

	private final ObjectMapper objectMapper;

	private final LoginServiceImpl loginservice;

	public KafkaConsumer(ObjectMapper objectMapper, LoginServiceImpl loginservice) {
		super();
		this.objectMapper = objectMapper;
		this.loginservice = loginservice;
	}

	@KafkaListener(topics = orderTopic)
	public void consumeUserDetails(String logindetails) throws JsonProcessingException {
		log.info("message consumed login details");
		Login login = deserializeLoginDetails(logindetails);
		loginservice.saveUserDetails(login);
	}

	public Login deserializeLoginDetails(String details) throws JsonMappingException, JsonProcessingException {
		Login login = objectMapper.readValue(details, Login.class);
		return login;

	}
}
