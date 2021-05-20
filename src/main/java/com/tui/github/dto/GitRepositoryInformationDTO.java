package com.tui.github.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GitRepositoryInformationDTO {
	
	@JsonProperty("repository_name")
	private String repositoryName;
	@JsonProperty("owner_name")
	private String ownerLogin;
	@JsonProperty("branch_information")
	private List<GitBranchInformationDTO> gitBranchInformationDTOs;

}
