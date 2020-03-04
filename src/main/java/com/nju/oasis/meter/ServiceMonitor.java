package com.nju.oasis.meter;

import io.micrometer.core.instrument.Metrics;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Aspect
@Component
public class ServiceMonitor {

    @Around("execution(* com.nju.oasis.controller.StatisticsController.getDocumentsWithMaxDownloads( .. ))")
    public Object topDownloadDocumentTimeout(ProceedingJoinPoint joinPoint) throws Throwable{
        Instant start = Instant.now();
        Object returnMessage = joinPoint.proceed();
        Instant end = Instant.now();
        Metrics.timer("top_document_download").record(Duration.between(start, end));
        return returnMessage;
    }

}
