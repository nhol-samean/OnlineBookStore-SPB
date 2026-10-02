package com.example.project_bsdb.exception;

public class BadRequestException extends RuntimeException {
	public BadRequestException(String message)
	{
		super(message);
	}
}
