package com.huadiao.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Pointcut("within(@org.springframework.stereotype.Controller *)")
    public void controller() {}

    @Pointcut("within(@org.springframework.stereotype.Service *)")
    public void service() {}

    @Pointcut("execution(* *(..))")
    public void methodPointcut() {}

    @Around("controller() && methodPointcut() || service() && methodPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        StringBuilder argsTypes = new StringBuilder();
        for (int i = 0; i < joinPoint.getArgs().length; i++) {
            argsTypes.append(joinPoint.getArgs()[i].getClass().getTypeName());
            if (i < joinPoint.getArgs().length - 1) {
                argsTypes.append(", ");
            }
        }

        StringBuilder argsValues = new StringBuilder();
        for (int i = 0; i < joinPoint.getArgs().length; i++) {
            argsValues.append(joinPoint.getArgs()[i]);
            if (i < joinPoint.getArgs().length - 1) {
                argsValues.append(", ");
            }
        }

        log.debug("Enter: {}.{}({}) with argument[s] = {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                argsTypes,
                argsValues);
        Object result = joinPoint.proceed();
        log.debug("Exit: {}.{}({}) with {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                argsTypes,
                result.getClass().getTypeName());
        return result;
    }
}