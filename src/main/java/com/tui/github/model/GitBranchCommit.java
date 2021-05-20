package com.tui.github.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class represent the GitBranch Latest Commit Details
 * @author
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GitBranchCommit {
	
	private String sha;
	private String url;

}
