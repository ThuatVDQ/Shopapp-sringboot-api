package com.project.shopapp.components;

import com.project.shopapp.utils.WebUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Arrays;
import java.util.Locale;

@RequiredArgsConstructor
@Component
public class LocalizationUtils {
    private final MessageSource messageSource;
    private final LocaleResolver localeResolver;

    public String getLocalizedMessage(String messageKey, Object... args) {
        HttpServletRequest request = WebUtils.getCurrentRequest();
        Locale locale = localeResolver.resolveLocale(request);
        System.out.println("Message key: " + messageKey); // logging message key
        System.out.println("Resolved locale: " + locale);
        System.out.println("Args: " + Arrays.toString(args));
        try {
            return messageSource.getMessage(messageKey, args, locale);
        } catch (NoSuchMessageException e) {
            System.out.println("Error: " + e.getMessage()); // logging error message
            return messageKey; // return the message key as a fallback
        }
    }
}
