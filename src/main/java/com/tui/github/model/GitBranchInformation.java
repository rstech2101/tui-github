package com.tui.github.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class represent the GitBranch Information
 * @author
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GitBranchInformation {

	private String name;
	private GitBranchCommit commit;
}
