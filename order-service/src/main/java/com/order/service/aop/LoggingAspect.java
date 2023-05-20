package com.order.service.aop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Autowired
    private ObjectMapper mapper;

    @Pointcut("within(com.order.service.controllers..*)")
    public void pointcut() {
    }

    @Before("pointcut()")
    public void logMethod(JoinPoint joinPoint) throws JsonProcessingException {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        try {
            log.info("Entered Requests with {} : {} ", signature.getMethod().getName(), mapper.writeValueAsString(joinPoint.getArgs()));
        } catch (JsonProcessingException e) {
            log.error("Error while converting", e);
        }
    }

    @AfterReturning(pointcut = "pointcut()", returning = "entity")
    public void logMethodAfter(JoinPoint joinPoint, ResponseEntity<?> entity) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        try {
            log.info("Response with {} : {}", signature.getMethod().getName(), mapper.writeValueAsString(entity.getBody()));
        } catch (JsonProcessingException exception) {
            log.error("Error while converting", exception);
        }
    }

    private Map<String, Object> getParameters(JoinPoint joinPoint) {
        CodeSignature signature = (CodeSignature) joinPoint.getSignature();
        HashMap<String, Object> map = new HashMap<>();
        String[] parameterNames = signature.getParameterNames();
        for (int i = 0; i < parameterNames.length; i++) {
            map.put(parameterNames[i], joinPoint.getArgs()[i]);
        }
        return map;
    }

}