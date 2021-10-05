package com.food.cpg.applicationhandlers;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

@Service(value = "ApplicationBeanHandler")
public class ApplicationBeanHandler implements ApplicationContextAware {

    private static ApplicationContext context;

    public static <T> T getBean(Class<T> beanClass) {
        return context.getBean(beanClass);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        context = applicationContext;
    }
}
