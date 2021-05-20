package com.tui.github.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URL;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class GithubControllerIntegrationTest {

	@LocalServerPort
    private int port;
	
	@Autowired
    private TestRestTemplate restTemplate;
	
	private HttpHeaders headers;
	
	private String methodUrl;
	
	@BeforeEach 
    void init() {
		headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));	
		methodUrl="http://localhost:" + port + "/git/api/v1/user/repos/test";
    }
	
	@Test
	@DisplayName("GET /v1/user/repos/{user_name} success 200")
    public void testGetUserReposDetails200() throws Exception {
		HttpEntity <Object> entity= new HttpEntity<>(headers);
		ResponseEntity<Object> response = restTemplate.exchange(
            new URL(methodUrl).toString(), 
            HttpMethod.GET, entity,Object.class);
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
    }
	
	@Test
	@DisplayName("GET /v1/user/repos/{user_name} failed 404")
    public void testGetUserReposDetails404() throws Exception {
		String methodUrl404="http://localhost:" + port + "/git/api/v1/user/repos/rstech21011212";
		HttpEntity <Object> entity= new HttpEntity<>(headers);
		ResponseEntity<Object> response = restTemplate.exchange(
            new URL(methodUrl404).toString(), 
            HttpMethod.GET, entity,Object.class);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());

    }
	
	@Test
	@DisplayName("GET /v1/user/repos/{user_name} failed 406")
    public void testGetUserReposDetails406() throws Exception {
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_XML));	
		HttpEntity <Object> entity= new HttpEntity<>(headers);
		ResponseEntity<Object> response = restTemplate.exchange(
            new URL(methodUrl).toString(), 
            HttpMethod.GET, entity,Object.class);
        assertEquals(HttpStatus.NOT_ACCEPTABLE.value(), response.getStatusCodeValue());

    }
}
