package com.example.book_api.exceptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.management.relation.RoleNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(RecordNotFoundException.class)
	public ResponseEntity<Object> handleRecordNotFound(RecordNotFoundException ex){
		
		ErrorResponse error = new ErrorResponse(ex.getLocalizedMessage(), Arrays.asList(ex.getMessage()));

		return new ResponseEntity<Object>(error,
				HttpStatus.OK);
	}
	

	@ExceptionHandler(DuplicateRecordException.class)
	public ResponseEntity<?> handleDaplicateRecoredException(DuplicateRecordException ex){

		ErrorResponse error = new ErrorResponse(ex.getLocalizedMessage(), Arrays.asList(ex.getMessage()));

		return new ResponseEntity<Object>(error,
				HttpStatus.OK);
	}

/*
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<String> errors = new ArrayList<String>();

		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getDefaultMessage());
		}
		for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.add(error.getDefaultMessage());
		}

		ErrorResponse error = new ErrorResponse("Validations failed", errors);


		return new ResponseEntity<Object>(error,
				HttpStatus.OK);
	}*/

	@ExceptionHandler(RoleNotFoundException.class)
	public ResponseEntity<?> handleRoleNotFoundException(RoleNotFoundException ex) {

		return ResponseEntity.status(HttpStatus.OK).body("Role Not Found");
	}

	@ExceptionHandler(DisabledException.class)
	public ResponseEntity<?> handleDisabledException(DisabledException ex) {

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("This Account is Disabled");
	}


	@ExceptionHandler(LockedException.class)
	public ResponseEntity<?> handleLockedException(LockedException ex) {

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("This Account is Locked");
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException ex) {

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Bad Credentials");
	}


	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<Object> handleAllRuntimeException(RuntimeException ex){
		ErrorResponse error = new ErrorResponse(ex.getLocalizedMessage(), Arrays.asList(ex.getMessage()));
		return new ResponseEntity<Object>(error,
				HttpStatus.OK);
	}
/*
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleAllException(Exception ex,WebRequest webRequest){
		ex.printStackTrace();
		logger.error(ex.getMessage());
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		ErrorResponse error = new ErrorResponse(ex.toString(), details);
		return new ResponseEntity<Object>(error,
				HttpStatus.OK);
	}*/


}
