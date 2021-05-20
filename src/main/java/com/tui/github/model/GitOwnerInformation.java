package com.tui.github.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class represent the GitOwner Information in which user fetching the public repos count.
 * @author
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GitOwnerInformation {

	private String login;
	private Long id;
	private String name;
	@JsonProperty("public_repos")
	private String publicRepos;
}
