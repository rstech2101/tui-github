package com.tui.github.service;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import com.tui.github.dto.GitRepositoryInformationDTO;
import com.tui.github.model.GitBranchCommit;
import com.tui.github.model.GitBranchInformation;
import com.tui.github.model.GitOwnerInformation;
import com.tui.github.model.GitRepositoryInformation;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class GithubApiServiceTest {

	@Autowired
	private IGithubApiService githubApiService;

	@MockBean
	private RestTemplate restTemplate;
	
	private String userName="testRobin";
	
	@Test
	@DisplayName(" Test GetOwnerInformation")
	public void testGetOwnerInformation() throws Exception {
		GitOwnerInformation gitOwnerInformation=new GitOwnerInformation(userName, 1l, "Test Robin", "2");
		ResponseEntity<GitOwnerInformation> response=new ResponseEntity<GitOwnerInformation>(gitOwnerInformation,HttpStatus.OK);
		when(restTemplate.exchange(Mockito.any(String.class),
                Mockito.<HttpMethod> any(),
                Mockito.<HttpEntity<GitOwnerInformation>> any(),
                Mockito.<Class<GitOwnerInformation>> any())).thenReturn(response);
		GitOwnerInformation returnedGitOwnerInformation = githubApiService.getOwnerInformation(userName);
		// Assert the response
		Assertions.assertEquals(userName, returnedGitOwnerInformation.getLogin(), "getOwnerInformation should return testRobin as login");

	}
	
	@Test
	@DisplayName(" Test GetRepositoryInfoArray")
	public void testGetRepositoryInfoArray() throws Exception {
		GitRepositoryInformation[] gitRepositoryInformationArray=this.getGitRepositoryInformationArray();
		ResponseEntity<GitRepositoryInformation[]> response=new ResponseEntity<GitRepositoryInformation[]>
		(gitRepositoryInformationArray,HttpStatus.OK);
		when(restTemplate.exchange(Mockito.any(String.class),
                Mockito.<HttpMethod> any(),
                Mockito.<HttpEntity<GitRepositoryInformation>> any(),
                Mockito.<Class<GitRepositoryInformation[]>> any())).thenReturn(response);
		GitRepositoryInformation[] returnedRepositoryInformation = githubApiService.getRepositoryInfoArray(userName,"2");
		// Assert the response
		Assertions.assertEquals(returnedRepositoryInformation.length, gitRepositoryInformationArray.length,
				"getRepositoryInfoArray should return 2 repositories");
		Assertions.assertEquals(returnedRepositoryInformation[0].getName(), gitRepositoryInformationArray[0].getName(),
				"getRepositoryInfoArray should return same Name");

	}
	
	
	private GitRepositoryInformation[] getGitRepositoryInformationArray() {
		GitRepositoryInformation[] gitRepositoryInformationArray= {new GitRepositoryInformation("master", false),
				new GitRepositoryInformation("develop", false)};
		return gitRepositoryInformationArray;
	}

	@Test
	@DisplayName(" Test GetRepoInfoWithBranch")
	public void testGetRepoInfoWithBranch() throws Exception {
		List<GitRepositoryInformationDTO> gitRepositoryInformationDTOList=this.getGitRepositoryInformations();
		ResponseEntity<GitBranchInformation[]> response=new ResponseEntity<GitBranchInformation[]>(
				this.getGitBranchInformationArray(),HttpStatus.OK);
		when(restTemplate.exchange(Mockito.any(String.class),
                Mockito.<HttpMethod> any(),
                Mockito.<HttpEntity<GitBranchInformation>> any(),
                Mockito.<Class<GitBranchInformation[]>> any())).thenReturn(response);
		List<GitRepositoryInformationDTO> returnedGitInformationList = 
				githubApiService.getRepoInfoWithBranch(getGitRepositoryInformationArray(),userName, userName);
		// Assert the response
		Assertions.assertEquals(gitRepositoryInformationDTOList.size(), returnedGitInformationList.size(),
				"getRepoInfoWithBranchDTO should return same size");
		Assertions.assertEquals(gitRepositoryInformationDTOList.get(0).getOwnerLogin(), 
				returnedGitInformationList.get(0).getOwnerLogin(),"getRepoInfoWithBranchDTO should return same owner login");

	}
	
	private List<GitRepositoryInformationDTO> getGitRepositoryInformations() {
		List<GitRepositoryInformationDTO> gitRepositoryInformationDTOs=new ArrayList<>();
		GitRepositoryInformationDTO gitRepositoryInformationDTO=new GitRepositoryInformationDTO("master", userName, null);
		GitRepositoryInformationDTO gitRepositoryInformationDTO2=new GitRepositoryInformationDTO("develop", userName, null);
		gitRepositoryInformationDTOs.add(gitRepositoryInformationDTO);
		gitRepositoryInformationDTOs.add(gitRepositoryInformationDTO2);
		return gitRepositoryInformationDTOs;
	}
	
	private GitBranchInformation[] getGitBranchInformationArray() {
		GitBranchInformation[] gitBranchInformationArray= {new GitBranchInformation("master", 
				new GitBranchCommit("edc7b47652d9175ea197682ab88947a35536fe9f",null)),
				new GitBranchInformation("develop", 
						new GitBranchCommit("4b23bcc8ed0ec473e64d0b56d8fd722b709c8562",null))};
		return gitBranchInformationArray;
	}

}
