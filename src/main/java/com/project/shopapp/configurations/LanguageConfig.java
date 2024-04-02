package com.project.shopapp.configurations;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Locale;
import java.util.ResourceBundle;
@Configuration
public class LanguageConfig {
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("i18n.messages"); // tên cơ sở của các tệp tài liệu ngôn ngữ
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
