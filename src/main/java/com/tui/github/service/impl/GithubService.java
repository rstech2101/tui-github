package com.tui.github.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tui.github.dto.GitRepositoryInformationDTO;
import com.tui.github.model.GitOwnerInformation;
import com.tui.github.model.GitRepositoryInformation;
import com.tui.github.service.IGithubApiService;
import com.tui.github.service.IGithubService;
/**
 * This class will provide the DTO to Controller
 * @author
 *
 */
@Service
public class GithubService implements IGithubService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IGithubApiService githubApiService;
	
	/**
	 * This method fetch the git repository information of user and return DTO
	 * @return GitRepositoryInformationDTO
	 * @throws Exception 
	 */
	public List<GitRepositoryInformationDTO> getUserReposDetails(String userName) throws Exception{
		logger.info("Method getUserReposDetails started in Service Class");
		GitOwnerInformation gitOwnerInformation=githubApiService.getOwnerInformation(userName);
		GitRepositoryInformation[] repositoryInformationArray=null;
		if(gitOwnerInformation!=null && gitOwnerInformation.getPublicRepos()!=null) {
			repositoryInformationArray=githubApiService.getRepositoryInfoArray(userName,gitOwnerInformation.getPublicRepos());
		}
		List<GitRepositoryInformationDTO> repositoryInformationLists=null;
		if(repositoryInformationArray!=null) {
			repositoryInformationLists=githubApiService.getRepoInfoWithBranch(repositoryInformationArray,userName,
					gitOwnerInformation.getLogin());
		}
		logger.info("Method getUserReposDetails End in Service Class");
		return repositoryInformationLists;
	}
	
}
