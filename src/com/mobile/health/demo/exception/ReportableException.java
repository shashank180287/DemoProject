package com.mobile.health.demo.exception;

public class ReportableException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8540661520649698945L;

	public ReportableException() {
		super();
	}
	
	public ReportableException(String message) {
		super(message);
	}
	
	public ReportableException(String message, Throwable ex) {
		super(message, ex);
	}
}
