package com.vinsguru.webfluxpatterns.sec03.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vinsguru.webfluxpatterns.sec03.dto.OrchestationRequestContext;

public class DebugUtil {

    public static void print(OrchestationRequestContext context) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(context));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


    }

}
