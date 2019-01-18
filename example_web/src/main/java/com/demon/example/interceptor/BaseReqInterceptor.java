package com.demon.example.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

import com.alibaba.fastjson.JSON;
import com.demon.example.common.ErrorCode;
import com.demon.example.common.NoParam;
import com.demon.example.common.NoSession;
import com.demon.example.protocol.BaseReq;
import com.demon.example.protocol.BaseResp;
import com.demon.example.util.ResponseUtils;

/**
 * 签名校验拦截器
 */
public class BaseReqInterceptor implements HandlerInterceptor {

    protected static Logger logger = LoggerFactory.getLogger(BaseReqInterceptor.class);
/*    session 校验服务
    @Autowired
    private SessionService sessionService;
*/    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        
        // Bootstrap
        if (handler instanceof ParameterizableViewController){
            ParameterizableViewController controller = (ParameterizableViewController) handler;
            controller.getApplicationContext();
            logger.info("Controller:{}", controller);
            logger.info("ApplicationContext:{}", controller.getApplicationContext());
            return true;
        }
        
        // Invalid Handler
        if (!(handler instanceof HandlerMethod)){
            logger.info("Invalid Handler:{}", handler);
            return false;
        }
        
        // Request Mapping
        HandlerMethod method = (HandlerMethod) handler;
        NoParam np = method.getBean().getClass().getAnnotation(NoParam.class);
        if (np != null){
            logger.info("NoParams method, uri:{}", request.getRequestURI());
            return true;
        }
        
        // Validate request data format and sign
        BaseReq baseReq = new BaseReq();
        baseReq.data = request.getParameter("data");
        baseReq.time = request.getParameter("time");
        baseReq.sessionid = request.getParameter("sessionid");
        baseReq.sign = request.getParameter("sign");
        
        int status = baseReq.validate();
        logger.info("validate status:{}, uri:{}, baseReq:{}", status, request.getRequestURI(),
                JSON.toJSONString(baseReq));
        if (status != 0) {
            writeResp(response, new BaseResp(status, ErrorCode.getName(status)));
            return false;
        }
        
        NoSession ns = method.getBean().getClass().getAnnotation(NoSession.class);
        if (ns != null){
            return true;
        }
        
        if (StringUtils.isEmpty(baseReq.sessionid)){
            logger.warn("sessionid empty, uri:{}, baseReq:{}", request.getRequestURI(),
                    JSON.toJSONString(baseReq));
            writeResp(response, new BaseResp(ErrorCode.SessionError.code(), "sessionid empty"));
            return false;
        }
        
        // Validate sessionid and set playerId
        /*Long playerId = sessionService.validate(baseReq.sessionid);
        logger.info("playerId:{}, sessionid:{}", playerId, baseReq.sessionid);
        
        if (playerId == null || playerId <= 0){
            logger.warn("sessionid's playerId not found, uri:{}, baseReq:{}", request.getRequestURI(),
                    JSON.toJSONString(baseReq));
            writeResp(response, new BaseResp(ErrorCode.SessionError.code(), "sessionid invalid"));
            return false;
        }
        
        request.setAttribute("playerId", playerId);*/
        
        return true;
    }
    
    /**
     * 写回响应结果到客户端，并返回过滤结果
     * @param response
     * @param baseResp
     * @return
     * @throws IOException
     */
    private void writeResp(HttpServletResponse response, BaseResp baseResp) throws IOException{
        ResponseUtils.writeResponse(response, JSON.toJSONString(baseResp));
    }

    @Override
    public void postHandle(HttpServletRequest request, 
            HttpServletResponse response, Object handler, ModelAndView modelAndView) 
            throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, 
            HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }

}
