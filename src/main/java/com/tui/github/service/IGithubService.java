package com.tui.github.service;

import java.util.List;

import com.tui.github.dto.GitRepositoryInformationDTO;

public interface IGithubService {

	List<GitRepositoryInformationDTO> getUserReposDetails(String userName) throws Exception;
}
