package com.swd.bike.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Random;

@Slf4j
@Aspect
@Component
public class RequestLogging {

    @Around("execution(* com.swd.bike.controller.*.*Controller.*(..))")
    public Object logging(ProceedingJoinPoint joinPoint) throws Throwable {
        Long startTime = System.currentTimeMillis();
        Random random = new Random();
        String trace = String.format("%03d", random.nextInt(999));
        log.debug("Request[{}]: {}.{}() with argument[s] = {}",
                trace,
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                Arrays.toString(joinPoint.getArgs())
        );
        Object result = joinPoint.proceed();

        Long endTime = System.currentTimeMillis();
        log.debug("Response[{}]: {}.{}() with response = {}, process time = {} milliseconds",
                trace,
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), result, (endTime - startTime));
        return result;
    }
}