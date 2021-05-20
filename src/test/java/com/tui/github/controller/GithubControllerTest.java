package com.tui.github.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.tui.github.dto.GitRepositoryInformationDTO;
import com.tui.github.service.IGithubService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GithubControllerTest {

	@MockBean
	private IGithubService gitHubFetchService;

	@Autowired
	private MockMvc mockMvc;
	
	private String requestUrl;
	private String userName;

	@BeforeEach
	void init() {
		requestUrl="/git/api/v1/user/repos/{user_name}";
		userName="testRobin";
	}
	@Test
	@DisplayName("GET /v1/user/repos/{user_name} success 200")
	void testGetUserReposDetails200() throws Exception {
		GitRepositoryInformationDTO gitRepositoryInformationDTO=new GitRepositoryInformationDTO("master", userName, null);
		GitRepositoryInformationDTO gitRepositoryInformationDTO2=new GitRepositoryInformationDTO("develop", userName, null);
		List<GitRepositoryInformationDTO> gitRepositoryInformationList=new ArrayList<>(2);
		gitRepositoryInformationList.add(gitRepositoryInformationDTO);
		gitRepositoryInformationList.add(gitRepositoryInformationDTO2);
		doReturn(gitRepositoryInformationList).when(gitHubFetchService).getUserReposDetails(userName);
		
		mockMvc.perform(get(requestUrl,userName).
				header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE))
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(2)));
	}
	
	@Test
	@DisplayName("GET /v1/user/repos/{user_name} failed 406")
	void testGetUserReposDetails406() throws Exception {
		doReturn(null).when(gitHubFetchService).getUserReposDetails(userName);
		mockMvc.perform(get(requestUrl,userName).
				header(HttpHeaders.ACCEPT, MediaType.APPLICATION_XML_VALUE))
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().is(HttpStatus.NOT_ACCEPTABLE.value()));
	}

	@Test
	@DisplayName("GET /v1/user/repos/{user_name} failed 404")
	void testGetUserReposDetails404() throws Exception {
		doReturn(null).when(gitHubFetchService).getUserReposDetails(userName);
		mockMvc.perform(get(requestUrl,userName).
				header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE))
		.andExpect(status().isOk());
	}
}
