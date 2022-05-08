package com.mvc.cryptovault.dashboard.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringContextUtil implements BeanFactoryAware {
    private static BeanFactory beanFactory;

    @Override
    public void setB