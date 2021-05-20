package com.tui.github.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude
public class GitBranchInformationDTO {
	@JsonProperty("branch_name")
	private String branchName;
	@JsonProperty("last_commit_sha")
	private String lastCommitSHA;

}
