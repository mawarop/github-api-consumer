package com.gmail.oprawam.githubapiconsumer.config;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.HashMap;
import java.util.Map;

@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {
    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        // todo
        var errorAttributes = super.getErrorAttributes(request, options);
        var newErrorAttributes = new HashMap<String, Object>();
        newErrorAttributes.put("status", errorAttributes.get("status"));
        newErrorAttributes.put("Message", errorAttributes.get("message"));
        return newErrorAttributes;
    }
}
