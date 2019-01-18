package com.demon.example.exception;

/**
 * 异常类封装
 */
public class ValidateException extends IllegalArgumentException {

	private static final long serialVersionUID = -9108493409435273453L;
	private int status;
	private Exception origin;
	
	public ValidateException() {
	}
	
	public ValidateException(int status, String msg) {
		super(msg);
		this.status = status;
	}
	
	public ValidateException(Exception origin, int status, String msg) {
		super(msg);
		this.status = status;
		this.origin = origin;
	}
	
	public int getStatus() {
		return status;
	}
	public Exception getOrigin() {
		return origin;
	}
	
}
