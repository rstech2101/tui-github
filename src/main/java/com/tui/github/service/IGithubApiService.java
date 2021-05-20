package com.tui.github.service;

import java.util.List;

import com.tui.github.dto.GitRepositoryInformationDTO;
import com.tui.github.model.GitOwnerInformation;
import com.tui.github.model.GitRepositoryInformation;

public interface IGithubApiService {
	
	public GitOwnerInformation getOwnerInformation(String userName);
	
	public GitRepositoryInformation[] getRepositoryInfoArray(String userName, String publicRepos);
	
	public List<GitRepositoryInformationDTO> getRepoInfoWithBranch(
			GitRepositoryInformation[] repositoryInformationArray, String userName, String ownerLogin);

}
