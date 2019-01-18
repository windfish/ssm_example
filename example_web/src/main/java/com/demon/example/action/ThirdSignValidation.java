package com.demon.example.action;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

public interface ThirdSignValidation {

    static final Logger logger = LoggerFactory.getLogger(ThirdSignValidation.class);
    static final Charset charset = Charset.forName("utf8");
    
    public default boolean signValid(String sign, JSONObject obj, String userKey){
        if(obj == null || obj.isEmpty() 
                || sign == null || sign.isEmpty()
                || userKey == null || userKey.isEmpty()){
            return false;
        }
        List<String> keys = new ArrayList<>();
        for(String key: obj.keySet()){
            if("sign".equals(key)){
                continue;
            }
            keys.add(key);
        }
        Collections.sort(keys);
        StringBuffer sb = new StringBuffer();
        for(String key: keys){
            sb.append(obj.get(key));
        }
        sb.append(userKey);
        
        byte[] bytes = sb.toString().getBytes(charset);
        String validSign = DigestUtils.md5Hex(bytes);
        if(validSign.equals(obj.get("sign"))){
            return true;
        }
        return false;
    }
    
}
