package com.shop.demo.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CustomMessage {

    @Autowired
    private MessageSource messageSource;

    public String get(String errorCode) {
        return messageSource.getMessage(errorCode, new Object[]{}, LocaleContextHolder.getLocale());
    }

    public String get(String errorCode, Object... args) {
        if (args == null) {
            return messageSource.getMessage(errorCode, null, LocaleContextHolder.getLocale());
        }

        Object[] params = new Object[args.length];
        for (int index = 0; index < params.length; index++) {
            var param = args[index];
            if (param instanceof String) {
                try {
                    params[index] = messageSource.getMessage((String) param, new String[]{},
                            LocaleContextHolder.getLocale());
                } catch (NoSuchMessageException e) {
                    params[index] = param;
                }
            } else {
                params[index] = param;
            }

        }

        return messageSource.getMessage(errorCode, params, LocaleContextHolder.getLocale());
    }

    public void addMessage(Map<String, List<String>> reasonFailMap, String fieldName, String message) {
        List<String> messageList = reasonFailMap.computeIfAbsent(fieldName, k -> new ArrayList<>());
        messageList.add(message);
    }
}