package com.tui.github.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;

import com.tui.github.exception.custom.ExceptionMessageConstant;
import com.tui.github.exception.custom.UserNotFoundException;

/**
 * ExceptionControllerHandler class handles all exceptions that happen and returns the corresponding status code accordingly
 * 
 * @author
 *
 */
@ControllerAdvice
public class ControllerExceptionHandler {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	/**
	 * handles HttpMediaTypeNotAcceptableException 
	 * 
	 * @param e
	 * @return error body with message
	 */
	@ExceptionHandler(value = HttpMediaTypeNotAcceptableException.class)
	public @ResponseBody ResponseEntity<ErrorMessage> mediaTypeNotAcceptablesErrorAction(HttpMediaTypeNotAcceptableException e) {
		ErrorMessage message = new ErrorMessage(
				HttpStatus.NOT_ACCEPTABLE.value(),
				ExceptionMessageConstant.APPLICATION_JSON_NOT_FOUND);
		return  ResponseEntity
		        .status(HttpStatus.NOT_ACCEPTABLE)
		        .contentType(MediaType.APPLICATION_JSON).body(message);
	}
	
	/**
	 * handles HttpClientErrorException
	 * 
	 * @param e
	 * @return error body with message
	 */
	@ExceptionHandler(value = HttpClientErrorException.class)
	public @ResponseBody ResponseEntity<ErrorMessage> httpClientErrorAction(HttpClientErrorException e) {
		ErrorMessage message = new ErrorMessage(
				e.getStatusCode().value(),
				e.getMessage());
		return new ResponseEntity<>(message, e.getStatusCode());
	}
	
	@ExceptionHandler(value = UserNotFoundException.class)
	public @ResponseBody ResponseEntity<ErrorMessage> userNotFoundExceptionAction(UserNotFoundException e) {
		ErrorMessage message = new ErrorMessage(
				HttpStatus.NOT_FOUND.value(),
				e.getMessage());
		return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
	}
	
	/**
	 * handles unhandled exceptions
	 * 
	 * @param e
	 * @return error body with message
	 */
	@ExceptionHandler(value = Exception.class)
	public @ResponseBody ResponseEntity<ErrorMessage> internalServerErrorAction(Exception e) {
		logger.info(getExceptionStacktrace(e));
		ErrorMessage er = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
		return new ResponseEntity<ErrorMessage>(er, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/**
	 * converts exception stacktrace to string
	 * 
	 * @param exception
	 * @return string of exception stacktrace
	 */
	private String getExceptionStacktrace(Exception exception) {
		StringWriter errors = new StringWriter();
		exception.printStackTrace(new PrintWriter(errors));
		return errors.toString();
	}

}
