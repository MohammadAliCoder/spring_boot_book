package com.example.book_api.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("execution(* com.example.book_api.services.BookService.save(..))")
    public void bookAddPointcut() {}

    @Pointcut("execution(* com.example.book_api.services.BookService.update(..))")
    public void bookUpdatePointcut() {}

    @Pointcut("execution(* com.example.book_api.services.PatronService.save(..))")
    public void patronAddTransactionPointcut() {}

    @Around("bookAddPointcut()")
    public Object logBookAddition(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            long duration = System.currentTimeMillis() - startTime;
            logger.info("Book added.......Time taken: {} ms", duration);
            return result;
        } catch (Exception e) {
            logger.error("Error adding book: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Around("bookUpdatePointcut()")
    public Object logBookUpdate(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            long duration = System.currentTimeMillis() - startTime;
            logger.info("Book updated.......Time taken: {} ms", duration);
            return result;
        } catch (Exception e) {
            logger.error("Error updating book: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Around("patronAddTransactionPointcut()")
    public Object logPatronTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            long duration = System.currentTimeMillis() - startTime;
            logger.info("Patron add transaction processed.......Time taken: {} ms", duration);
            return result;
        } catch (Exception e) {
            logger.error("Error processing patron transaction: {}", e.getMessage(), e);
            throw e;
        }
    }

}