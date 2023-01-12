package com.imax.stock.config;

import com.imax.stock.exception.IValidation;
import com.imax.stock.exception.CustomException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolationException;
import java.io.PrintWriter;
import java.io.StringWriter;

@Aspect
@Component
public class AspectConfig {


    @Pointcut("within(@org.springframework.web.bind.annotation.RestControllerAdvice * )")
    public void controllerAdvice() {
        /*
        This method contains controller Advice
         */
    }

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController * || @org.springframework.stereotype.Controller *)")
    public void controller() {
        /*
        This method contains all controller class methods
         */
    }

    @Pointcut("within(@org.springframework.stereotype.Service *)")
    public void service() {
        /*
        This method contains all service class methods
         */
    }

    @Pointcut("execution(public * *(..))")
    public void allMethod() {
        /*
        This method contains all methods
         */
    }

    @Before(value = "controller()")
    public void validate(JoinPoint joinPoint) throws CustomException {
        Object[] signatureArgs = joinPoint.getArgs();
        for (Object signatureArg : signatureArgs) {
            if (signatureArg instanceof IValidation) {
                ((IValidation) signatureArg).validate();
            }
        }
    }


    @AfterThrowing(pointcut = "controller() || controllerAdvice()) && allMethod()", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Exception exception) {
        if (exception instanceof CustomException ||
                exception instanceof ConstraintViolationException) {
            /*
                Ignored Log
             */
        } else {
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            exception.printStackTrace(printWriter);
        }
    }

}
