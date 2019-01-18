package com.demon.example.action;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.demon.example.common.ErrorCode;
import com.demon.example.common.NoParam;
import com.demon.example.common.NoSession;
import com.demon.example.exception.ValidateException;
import com.demon.example.protocol.BaseReq;
import com.demon.example.protocol.BaseResp;
import com.demon.example.util.ReflectUtils;
import com.demon.example.util.ServletUtils;

/**
 * 基础action
 */
@Controller
public abstract class BaseAction<Req, Resp> implements Validation, Ip {

	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	protected static ExecutorService executorService = Executors.newFixedThreadPool(4);
	
	@ModelAttribute
	public void validate(@ModelAttribute BaseReq baseReq, Model model){
		Req req = null;
		
		try {
			req = baseReq.get(getReqClass());
		} catch (Exception e) {
			logger.warn("parse req data to json exception, data:{}", baseReq.data);
			throw new ValidateException(ErrorCode.ParameterError.code(), ErrorCode.ParameterError.name());
		}
		
		if (req != null){
			model.addAttribute("req", req);
		}
		
		if (baseReq.sessionid != null){
			model.addAttribute("sessionid", baseReq.getSessionid());
		}
	}
	
	protected boolean isNoParamReq() {
		return this.getClass().getDeclaredAnnotation(NoParam.class) != null;
	}
	
	protected boolean isNoSessionReq() {
        return this.getClass().getDeclaredAnnotation(NoSession.class) != null;
    }
	
	@RequestMapping(produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String execute(@ModelAttribute("req") Req req, @ModelAttribute("sessionid") String sessionid,
			HttpServletRequest request, HttpServletResponse response) {
		
		Resp resp = null;
		BaseResp baseResp = null;
		try {
		    Long playerId = (Long) request.getAttribute("playerId");
		    
		    if(!isNoSessionReq()){
		        validate(playerId == null || playerId <= 0, ErrorCode.SessionError.code(), "sessionid invalid");
		    }
		    
		    if (!isNoParamReq()){
	            validate(req == null, ErrorCode.ParameterError.code(), "req null exception");
	            validateData(req);
	        }
		    
		    Cookie[] cookies = request.getCookies();
		    String barNumber = "0";
		    try{
    		    if(cookies != null){
        		    for(Cookie cookie: cookies){
        		        if("barNumber".equalsIgnoreCase(cookie.getName())){
        		            barNumber = cookie.getValue();
        		            logger.info("barNumber in cookie: {}", barNumber);
        		            break;
        		        }
        		    }
    		    }
		    }catch (Exception e) {
                logger.error("get cookies exception", e);
                barNumber = "0";
            }
		    
			resp = doAction(req, playerId, sessionid, barNumber);
			baseResp = new BaseResp(resp);
		} catch (ValidateException e) {
		    logger.info("ValidateException status:" + e.getStatus() + ", msg:" + e.getMessage());
	        if (e.getOrigin() != null){
	            logger.error("ValidateException origin exception:", e.getOrigin());
	        }
			 baseResp = new BaseResp(e.getStatus(), e.getMessage());
			log(req, e.getMessage(), sessionid);
		} catch (Exception e) {
			logger.error(this.getClass().getName() + " unknown exception, req:" + JSON.toJSONString(req), e);
			baseResp = new BaseResp(ErrorCode.Unknown.code(), "server exception");
			log(req, e.getMessage(), sessionid);
		}
		return JSON.toJSONString(baseResp);
	}
	
	/**
	 * 数据验证的方法，有错误直接 throw new ValidateException(code, message); 即可
	 */
	protected void validateData(Req req){
	}
	
	/**
	 * 具体的业务逻辑实现
	 */
	protected Resp doAction(Req req, Long playerId, String sessionId, String barNumber) throws Exception {
	    return doAction(req, playerId, sessionId);
	}
	protected Resp doAction(Req req, Long playerId, String sessionId) throws Exception {
	    return doAction(req, playerId);
	}
	protected Resp doAction(Req req, Long playerId) throws Exception {
	    return null;
	}
	
	/**
	 * 日志记录
	 */
	protected void log(Req req, String msg, String sessionId) {
	}
	
	/**
	 * 通过 sessionid 获取用户id
	 * @param sessionid
	 * @return
	 * @throws ValidateException
	 */
	protected long getPlayerIdBySessionId(String sessionid) throws ValidateException {
		Long playerId = 0L;
		if (playerId == null || playerId <= 0) {
			throw new ValidateException(ErrorCode.SessionError.code(), "会话不存在");
		}
		return playerId;
	}
	
	private ServletRequestAttributes getServletRequestAttributes(){
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		if (requestAttributes instanceof ServletRequestAttributes){
			return (ServletRequestAttributes) requestAttributes;
		}
		return null;
	}
    
	protected HttpServletRequest getRequest() throws Exception {
		ServletRequestAttributes requestAttributes = getServletRequestAttributes();
		if (requestAttributes == null) {
			return null;
		}
		return requestAttributes.getRequest();
	}
	
	/**
	 * 获取客户端IP
	 */
	protected String getRemoteAddr() {
		String ip = "";
		try {
			HttpServletRequest request = getRequest();
			if (request != null) {
				ip = ServletUtils.getRemoteAddr(getRequest());
			}
		} catch (Exception e) {
			logger.error("getRemoteAddr exception: ", e);
		}
		return ip;
	}
	
	@SuppressWarnings("unchecked")
	protected Class<Req> getReqClass(){
		return ReflectUtils.getClassGenricType(getClass(), 0);
	}
	
	@SuppressWarnings("unchecked")
	protected Class<Resp> getRespClass(){
		return ReflectUtils.getClassGenricType(getClass(), 1);
	}
	
}
