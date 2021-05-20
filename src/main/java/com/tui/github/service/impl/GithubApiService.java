package com.tui.github.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.tui.github.dto.GitBranchInformationDTO;
import com.tui.github.dto.GitRepositoryInformationDTO;
import com.tui.github.exception.custom.ExceptionMessageConstant;
import com.tui.github.exception.custom.UserNotFoundException;
import com.tui.github.model.GitBranchInformation;
import com.tui.github.model.GitOwnerInformation;
import com.tui.github.model.GitRepositoryInformation;
import com.tui.github.service.IGithubApiService;
import com.tui.github.util.ApplicationConstant;

/**
 * This Service Class used to Call the Github API
 * @author
 *
 */
@Service
public class GithubApiService implements IGithubApiService {

private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Value("${git.repository.url}")
	private String repositoryUrl;
	
	@Value("${git.user.url}")
	private String userUrl;
	
	@Value("${git.branch.url}")
	private String branchUrl;
	
	@Autowired
	private RestTemplate restTemplate;
	

	/**
	 * This method used to get the Github Repositories Information in Array
	 * @return GitRepositoryInformation[]
	 */
	public GitRepositoryInformation[] getRepositoryInfoArray(String userName, String publicRepos) {
		logger.info("Inside getRepositoryInfoArray method");
		HttpEntity <GitRepositoryInformation> entity = new HttpEntity<>(getHttpHeader());
		String repositoryUrlMethod=repositoryUrl.replaceAll(ApplicationConstant.USER_NAME, userName);
		repositoryUrlMethod=repositoryUrlMethod.replace(ApplicationConstant.PER_PAGE, publicRepos);
		return restTemplate.exchange(repositoryUrlMethod, HttpMethod.GET, entity, GitRepositoryInformation[].class).getBody();
	}

	/**
	 * This method used to get the Github Owner Information 
	 * @return GitOwnerInformation
	 */
	public GitOwnerInformation getOwnerInformation(String userName) {
		logger.info("Inside getOwnerInformation method");
		HttpEntity <GitOwnerInformation> entityForUser = new HttpEntity<>(getHttpHeader());
		String userUrlToFetch=userUrl.replace(ApplicationConstant.USER_NAME, userName);
		try {
			return restTemplate.exchange(userUrlToFetch, HttpMethod.GET, entityForUser, GitOwnerInformation.class).getBody();
		}catch (HttpClientErrorException e) {
			if(e.getMessage().contains(ExceptionMessageConstant.API_RATE_LIMIT)) {
				throw e;
			}
			throw new UserNotFoundException(userName+ExceptionMessageConstant.USER_NOT_FOUND);
		}
	}
	
	/**
	 * This method used to get the HttpHeader
	 * @return HttpHeaders
	 */
	private HttpHeaders getHttpHeader() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		return headers;
	}

	@Override
	public List<GitRepositoryInformationDTO> getRepoInfoWithBranch(
			GitRepositoryInformation[] repositoryInformationArray, String userName, String ownerLogin) {
		logger.info("method getRepoInfoWithBranchDTO started");
		List<GitRepositoryInformation> repositoryInformationLists=new ArrayList<GitRepositoryInformation>(
				Arrays.asList(repositoryInformationArray));
		repositoryInformationLists.removeIf(e->e.getFork()==true);
		List<GitRepositoryInformationDTO> gitRepositoryInformationList=null;
		if(!repositoryInformationLists.isEmpty()) {
			gitRepositoryInformationList=new ArrayList<>();
			for (GitRepositoryInformation gitRepositoryInformation : repositoryInformationLists) {
				GitRepositoryInformationDTO gitRepositoryInformationDTO=new GitRepositoryInformationDTO();
				gitRepositoryInformationDTO.setRepositoryName(gitRepositoryInformation.getName());
				gitRepositoryInformationDTO.setOwnerLogin(ownerLogin);
				GitBranchInformation[] gitBranchInformationArray=this.getBranchInformationArray(userName,
						gitRepositoryInformation.getName());
				if(gitBranchInformationArray!=null) {
					List<GitBranchInformationDTO> gitBranchInformationDTOs=new ArrayList<>();
					new ArrayList<GitBranchInformation>(Arrays.asList(gitBranchInformationArray)).forEach(branch->
					{
						GitBranchInformationDTO gitBranchInformationDTO=new GitBranchInformationDTO();
						gitBranchInformationDTO.setBranchName(branch.getName());
						gitBranchInformationDTO.setLastCommitSHA(branch.getCommit().getSha());
						gitBranchInformationDTOs.add(gitBranchInformationDTO);	
					});
					gitRepositoryInformationDTO.setGitBranchInformationDTOs(gitBranchInformationDTOs);
					gitRepositoryInformationList.add(gitRepositoryInformationDTO);
				}
			}
		}
		logger.info("method getRepoInfoWithBranchDTO completed");
		return gitRepositoryInformationList;
	}

	private GitBranchInformation[] getBranchInformationArray(String userName, String name) {
		HttpEntity <GitBranchInformation> entityForBranch = new HttpEntity<>(getHttpHeader());
		GitBranchInformation[] gitBranchInformationArray=restTemplate.exchange(
				getBranchUrl(userName,name), HttpMethod.GET, entityForBranch, GitBranchInformation[].class).getBody();
		return gitBranchInformationArray;
	}

	/**
	 * This method used to get the Branch Url
	 * @param userName
	 * @param repositoryName
	 * @return
	 */
	private String getBranchUrl(String userName, String repositoryName) {
		String branchUrlMethod=branchUrl;
		branchUrlMethod=branchUrlMethod.replace(ApplicationConstant.USER_NAME, userName); 
		branchUrlMethod=branchUrlMethod.replace(ApplicationConstant.BRANCH_NAME, repositoryName);
		return branchUrlMethod;
	}
}
