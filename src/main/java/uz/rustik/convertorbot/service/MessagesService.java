package uz.rustik.convertorbot.service;

import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class MessagesService {

    private final ResourceBundleMessageSource messageSource;

    public MessagesService(ResourceBundleMessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMessage(String key, Locale lang) {
        return messageSource.getMessage(key, null, lang);
    }
}
