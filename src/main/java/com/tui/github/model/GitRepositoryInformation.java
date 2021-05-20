package com.tui.github.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class represent the GitRepository Information
 * @author
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GitRepositoryInformation {
	
	private String name;
	private Boolean fork;
}
