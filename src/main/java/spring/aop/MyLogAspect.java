package spring.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author tangj
 * @date 2018/9/9 18:18
 */
@Component
@Aspect
public class MyLogAspect {

    @Pointcut("execution(* spring.aop.*.*(..))")
    public void pointCut(){

    }

    @Before("pointCut()")
    public void beforeMethod(JoinPoint joinPoint){
        String methodName = joinPoint.getSignature().getName();
        System.out.println("before method:   "+methodName+" time :"+System.currentTimeMillis());
    }

    @AfterReturning("pointCut()")
    public void afterMethod(JoinPoint joinPoint){
        String methodName = joinPoint.getSignature().getName();
        System.out.println("after method:   "+methodName+" time :"+System.currentTimeMillis());

    }
}
