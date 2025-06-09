package io.github.jotabrc.ov_ims_inv.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component @Aspect @Slf4j
public class Logging {

    @Around("@annotation(io.github.jotabrc.ov_ims_inv.logging.Log)")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getTarget().getClass().getName();

        log.info("Execution of {} started...", className);
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long delta = System.currentTimeMillis() - start;
        log.info("Executed {} with duration of ({}ms)", className, delta);

        return result;
    }
}
