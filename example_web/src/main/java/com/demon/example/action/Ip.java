package com.demon.example.action;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface Ip {

    static final Logger logger = LoggerFactory.getLogger(Ip.class);

    public default String getIp(HttpServletRequest request) {
        try {
            if (request != null) {
                String ip;
                if ((ip = request.getHeader("X-Real-IP")) != null && ip.length() > 0){
                    logger.info("X-Real-IP:{}", ip);
                    return ip;
                }
            }
        } catch (Exception e) {
            logger.error("getIp X-Real-IP exception: ", e);
        }
        return request.getRemoteAddr();
    }

}
