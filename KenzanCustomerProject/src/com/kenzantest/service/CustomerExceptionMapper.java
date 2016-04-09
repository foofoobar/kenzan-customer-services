package com.kenzantest.service;

import java.util.Set;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import java.util.logging.*;


@Provider
public class CustomerExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

	Logger logger = Logger.getLogger(CustomerExceptionMapper.class.getName());
	
	@Override
	public Response toResponse(ConstraintViolationException exception) {
		logger.log(Level.INFO, "Invalid Resquest or Response (ConstraintViolationException)");
		
		Response response = Response.status(Response.Status.BAD_REQUEST)
							.type(MediaType.APPLICATION_JSON_TYPE)
				.entity(new ErrorResponse(Response.Status.BAD_REQUEST.name(), exception.getConstraintViolations())).build();
	    
		logger.log(Level.INFO, "Payload " + response);
		return response;
	}
	
	private class ErrorResponse implements Serializable{
		private static final long serialVersionUID = 2610883718946239136L;
		private String code;
		private List<String> errorMessages;
		
		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public List<String> getErrorMessages() {
			return errorMessages;
		}

		public void setErrorMessages(List<String> errorMessages) {
			this.errorMessages = errorMessages;
		}

		
		public ErrorResponse(String code, Set<ConstraintViolation<?>> violations) {
			this.code = code;
			this.errorMessages = new ArrayList<String>();
			for (ConstraintViolation<?> violation : violations) {
				errorMessages.add(violation.getMessage());
			}
		}
	}

}
