package com.demon.example.common;

import java.util.HashMap;
import java.util.Map;

/**
 * API错误码
 */
public enum ErrorCode {

	Unknown(1000),
	Permission(1001),
	Paging(1002),
	GameNotExist(1003),
	ParameterError(1004),
	SessionError(1005),
	
	ReqDataError(1006),
	ReqTimeError(1007),
	ReqSessionidError(1008),
	ReqSignError(1009),
	
	Login(10200),
	Logout(10300),
	
	;
	
	private int code;
	private ErrorCode(int code) {
		this.code = code;
	}
	
	public int code(){
		return code;
	}
	
	private static Map<Integer, ErrorCode> map = new HashMap<>();
	static {
		for (ErrorCode bean : values()) {
			map.put(bean.code(), bean);
		}
	}
	
	public static String getName(int code){
		ErrorCode bean = map.get(code);
		return bean != null ? bean.name() : "";
	}
	
}
