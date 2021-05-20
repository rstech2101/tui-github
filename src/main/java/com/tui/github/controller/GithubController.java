package com.tui.github.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tui.github.dto.GitRepositoryInformationDTO;
import com.tui.github.service.IGithubService;
/**
 * Controller Class to call the service to get user Repositories Details
 * @author
 *
 */
@RestController
@RequestMapping("/git/api")
public class GithubController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IGithubService gitHubFetchService;

	@RequestMapping(method = RequestMethod.GET, path = "/v1/user/repos/{user_name}", produces = "application/json")
	public @ResponseBody ResponseEntity<List<GitRepositoryInformationDTO>> getUserReposDetails(
			@PathVariable(name="user_name",required = true) String userName) throws Exception{
		logger.info("getRepositoryInformation Requested for UserName ::"+userName);
		List<GitRepositoryInformationDTO> gitRepositoryInformationList=null;
		gitRepositoryInformationList=gitHubFetchService.getUserReposDetails(userName);
		return new ResponseEntity<>(gitRepositoryInformationList, HttpStatus.OK);
	}

}
