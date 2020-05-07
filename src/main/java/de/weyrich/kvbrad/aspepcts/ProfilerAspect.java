package de.weyrich.kvbrad.aspepcts;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ProfilerAspect {

    private final Logger logger = LoggerFactory.getLogger(ProfilerAspect.class);

    @Around("@annotation(ActivateProfiler)")
    public Object profilerAdvice(ProceedingJoinPoint joinPoint) throws Throwable{

        final long start = System.currentTimeMillis();
        final Object proceed = joinPoint.proceed();
        final long duration = System.currentTimeMillis() -start;
        logger.info("PROFILER: Execution of {} took {} ms", joinPoint.getSignature(), duration);
        return proceed;
    }
}
