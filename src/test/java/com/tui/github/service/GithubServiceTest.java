package com.tui.github.service;

import static org.mockito.Mockito.doReturn;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.tui.github.dto.GitRepositoryInformationDTO;
import com.tui.github.model.GitOwnerInformation;
import com.tui.github.model.GitRepositoryInformation;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class GithubServiceTest {

	@Autowired
	private IGithubService githubService;

	@MockBean
	private IGithubApiService githubApiService;

	private String userName="testRobin";

	@Test
	@DisplayName(" Test GitRepositoryInformation")
	public void testGetGitRepositoryInformation() throws Exception {
		List<GitRepositoryInformationDTO> gitRepositoryInformationDTOList=this.getGitRepositoryInformations();
		GitOwnerInformation gitOwnerInformation=new GitOwnerInformation(userName, 1l, "Test Robin", "2");
		doReturn(gitOwnerInformation).when(githubApiService).getOwnerInformation(userName);
		GitRepositoryInformation[] gitRepositoryInfoArray=this.getGitRepositoryInformationArray();
		doReturn(gitRepositoryInfoArray).when(githubApiService).getRepositoryInfoArray(userName,
				gitOwnerInformation.getPublicRepos());
		doReturn(gitRepositoryInformationDTOList).when(githubApiService).getRepoInfoWithBranch(
				gitRepositoryInfoArray,userName,gitOwnerInformation.getLogin());

		// Execute the service call
		List<GitRepositoryInformationDTO> returnedInformationList = githubService.getUserReposDetails(userName);
		// Assert the response
		Assertions.assertEquals(2, returnedInformationList.size(), "getGitRepositoryInformationDTO should return 2");

	}

	private List<GitRepositoryInformationDTO> getGitRepositoryInformations() {
		List<GitRepositoryInformationDTO> gitRepositoryInformationDTOs=new ArrayList<>();
		GitRepositoryInformationDTO gitRepositoryInformationDTO=new GitRepositoryInformationDTO("master", userName, null);
		GitRepositoryInformationDTO gitRepositoryInformationDTO2=new GitRepositoryInformationDTO("develop", userName, null);
		gitRepositoryInformationDTOs.add(gitRepositoryInformationDTO);
		gitRepositoryInformationDTOs.add(gitRepositoryInformationDTO2);
		return gitRepositoryInformationDTOs;
	}

	private GitRepositoryInformation[] getGitRepositoryInformationArray() {
		GitRepositoryInformation[] gitRepositoryInformationArray= {new GitRepositoryInformation("master", false),
				new GitRepositoryInformation("develop", false)};
		return gitRepositoryInformationArray;
	}
	
}
