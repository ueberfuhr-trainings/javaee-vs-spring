package de.sample.javax.spring.domain;

import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.autoproxy.AbstractBeanFactoryAwareAdvisingPostProcessor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class LogPerformancePostProcessor extends AbstractBeanFactoryAwareAdvisingPostProcessor
    implements InitializingBean {

    private final MethodInterceptor logPerformanceInterceptor = ic -> {
        String methodName = ic.getMethod().getName();
        long ts = System.currentTimeMillis();
        Object returnValue = ic.proceed();
        long ts2 = System.currentTimeMillis();
        System.out.println("Methode " + methodName + ": " + (ts2-ts) + "ms");
        return returnValue;
    };

    @Override
    public void afterPropertiesSet() {
        Pointcut pointcut = new AnnotationMatchingPointcut(null, LogPerformance.class, true);
        this.advisor = new DefaultPointcutAdvisor(pointcut, logPerformanceInterceptor);
    }
}
