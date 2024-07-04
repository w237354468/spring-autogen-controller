package org.lcdpframework.web.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.lcdpframework.server.event.impl.LcdpReceiveRequestEvent;
import org.lcdpframework.server.util.LcdpEventUtil;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DynamicRequestAspect {

    @Pointcut("execution(* org.lcdpframework.web.DynamicRequestHandler.*(..))")
    public void lcdpRequestPointCut() {
    }

    @Around(value = "lcdpRequestPointCut()")
    public Object aroundLcdpRequest(ProceedingJoinPoint pjp) {

        Object[] args = pjp.getArgs();

        try {
            LcdpEventUtil.publishEvent(new LcdpReceiveRequestEvent(args)); // receive request event

            return pjp.proceed(pjp.getArgs());
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
