package com.demon.example.util;

import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResponseUtils {

	private static Logger logger = LoggerFactory.getLogger(ResponseUtils.class);
	
	public static void writeResponse(HttpServletResponse response, String responseData) {
		try {
			byte[] data = responseData.getBytes("utf-8");
			response.setContentType("application/json;charset=UTF-8");
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setContentLength(data.length);
			OutputStream output = response.getOutputStream();
			output.write(data);
		} catch (Exception e) {
			logger.error("writeResponse exception: ", e);
		}
	}
	
}
