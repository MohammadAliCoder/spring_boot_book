package com.example.book_api.exceptions;

import java.time.LocalDateTime;
import java.util.List;

public class ErrorResponse {
	
	private Boolean success;
	private String message;
	private String dateTime;
	private List<String> details;
	
	
	public ErrorResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ErrorResponse(String message, List<String> details) {
		super();
		this.message = message;
		this.details = details;
		this.success = Boolean.FALSE;
		this.dateTime = LocalDateTime.now().toString();
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDateTime() {
		return dateTime;
	}


	public List<String> getDetails() {
		return details;
	}

	public void setDetails(List<String> details) {
		this.details = details;
	}
	

}
