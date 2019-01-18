package com.demon.example.protocol;

import java.nio.charset.Charset;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.demon.example.common.ErrorCode;

/**
 * 基础请求数据封装类
 */
public class BaseReq {

	public String data;
	public String time;
	public String sessionid;
	public String sign;
	
	public <Req> Req get(Class<Req> clazz){
		if (StringUtils.isEmpty(data)){
			return null;
		}
		return JSON.parseObject(data, clazz);
	}

	private static final String EMPTY_DATA = new JSONObject().toJSONString();
	
	private static final int SIGN_LENGTH = DigestUtils.md5Hex(EMPTY_DATA).length();
	
	private static final Charset charset = Charset.forName("utf8");
	
	public int validate() {
		if (data == null || data.length() < EMPTY_DATA.length()){
			return ErrorCode.ReqDataError.code();
		}
		if (time == null){
			return ErrorCode.ReqTimeError.code();
		}
		if (sessionid == null){
			return ErrorCode.ReqSessionidError.code();
		}
		
		if (sign == null || sign.length() < SIGN_LENGTH){
			return ErrorCode.ReqSignError.code();
		}
		
		byte[] bytes = new StringBuffer(getLength())
							.append(data).append(time).append(sessionid)
							.toString()
							.getBytes(charset);
		
		String validSign = DigestUtils.md5Hex(bytes);
		
		return validSign.equals(sign) ? 0 : ErrorCode.ReqSignError.code();
	}
	
	private int getLength(){
		return data.length() + time.length() + sessionid.length();
	}
	
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getSessionid() {
		return sessionid;
	}

	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
	
}
