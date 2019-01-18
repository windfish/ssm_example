package com.demon.example.protocol;

import com.alibaba.fastjson.JSONObject;

/**
 * 基础数据响应封装类
 */
public class BaseResp {

	public Integer status;
	public String msg;
	public Object data;
	
	private static final String EMPTY_DATA = new JSONObject().toJSONString();
	
	public BaseResp() {
		this.status = 0;
		this.msg = "";
		this.data = EMPTY_DATA;
	}
	
	public BaseResp(int status) {
		this.status = status;
	}
	
	public BaseResp(int status, String msg) {
		this.status = status;
		this.msg = msg;
	}
	
	public <Resp> BaseResp(Resp resp) {
		this.status = 0;
		this.msg = "";
		this.data = resp;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
}
