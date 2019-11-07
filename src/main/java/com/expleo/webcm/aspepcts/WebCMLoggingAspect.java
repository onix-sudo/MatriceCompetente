package com.expleo.webcm.aspepcts;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class WebCMLoggingAspect {

    //Setup logger
    private Logger myLogger = Logger.getLogger(getClass().getName());

    //setup pointcut declarations
    @Pointcut("execution(* com.mov.controllers.*.*(..))")
    private void forControllersPackage(){ }

    @Pointcut("execution(* com.mov.config.*.*(..))")
    private void forServicePackage(){ }

    @Pointcut("execution(* com.mov.dao.*.*(..))")
    private void forDAOPackage(){ }

    @Pointcut("forControllersPackage() || forServicePackage() || forDAOPackage()")
    private void forAppFlow(){}

    //add @Before advice

    @Before("forAppFlow()")
    public void before(JoinPoint theJointPoint){
        //display the method we are calling
        String theMethod = theJointPoint.getSignature().toString();
        myLogger.info("----> in @Before: calling method: " + theMethod);

        //display the arguments to the method

        //get the arguments
        Object[] args = theJointPoint.getArgs();

        //loop thru and display argumets
        for (Object temp:args){
            myLogger.info("====> Argument:" + temp);
        }
    }

    //add @AfterReturning advice
    @AfterReturning(
            pointcut = "forAppFlow()",
            returning = "result"
    )
    public void afterReturning(JoinPoint theJoinPoint, Object result){

        //display method we are returning from
        String theMethod = theJoinPoint.getSignature().toString();
        myLogger.info("----> in @AfterReturning: from method: " + theMethod);

        //display the data returned
        myLogger.info("====> result: " + result);
    }
}
