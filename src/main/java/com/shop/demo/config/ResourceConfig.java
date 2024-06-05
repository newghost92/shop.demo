package com.shop.demo.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class ResourceConfig {

    @Bean(name = "messageSource")
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource bean = new ReloadableResourceBundleMessageSource();
        bean.addBasenames("classpath:i18n/messages", "classpath:i18n/labels");
        bean.setDefaultEncoding("UTF-8");
        return bean;
    }

    @Bean(name = "labelSource")
    public MessageSource labelSource() {
        ReloadableResourceBundleMessageSource bean = new ReloadableResourceBundleMessageSource();
        bean.addBasenames("classpath:i18n/labels");
        bean.setDefaultEncoding("UTF-8");
        return bean;
    }

    @Bean(name = "validationMessages")
    public MessageSource validationMessagesSource() {
        ReloadableResourceBundleMessageSource bean = new ReloadableResourceBundleMessageSource();
        bean.addBasenames("classpath:i18n/validationMessages", "classpath:i18n/messages",
            "classpath:i18n/labels");
        bean.setDefaultEncoding("UTF-8");
        return bean;
    }

    @Bean
    public LocalValidatorFactoryBean getValidator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(validationMessagesSource());
        return bean;
    }
}
