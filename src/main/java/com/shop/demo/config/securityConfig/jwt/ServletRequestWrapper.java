package com.shop.demo.config.securityConfig.jwt;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

@SuppressWarnings("unchecked")
public class ServletRequestWrapper extends HttpServletRequestWrapper {

    public ServletRequestWrapper(HttpServletRequest request) {
        super(request);
        headerMap = new HashMap<>();
    }

    private final Map<String, String> headerMap;

    public void addHeader(String name, String value){
        headerMap.put(name, value);
    }

    @Override
    public String getHeader(String name){
        Object value;
        if((value = headerMap.get(""+name)) != null)
            return value.toString();
        else
            return ((HttpServletRequest)getRequest()).getHeader(name);

    }
}
