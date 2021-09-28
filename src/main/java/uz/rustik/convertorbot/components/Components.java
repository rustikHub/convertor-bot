package uz.rustik.convertorbot.components;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import uz.rustik.convertorbot.bot.TelegramFacade;
import uz.rustik.convertorbot.bot.TranslatorBot;
import uz.rustik.convertorbot.db.entity.enums.BotState;
import uz.rustik.convertorbot.tool.Convertor;
import uz.rustik.convertorbot.utils.DocxConvertor;
import uz.rustik.convertorbot.utils.PdfConvertor;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Component
public class Components {

    private final TelegramFacade telegramFacade;

    public Components(@Lazy TelegramFacade telegramFacade) {
        this.telegramFacade = telegramFacade;
    }

    @Bean
    public Convertor convertor() {
        return new Convertor();
    }

    @Bean
    public PdfConvertor pdfConvertor() {
        return new PdfConvertor();
    }

    @Bean
    public DocxConvertor docxConvertor() {
        return new DocxConvertor();
    }


    @Bean
    public TranslatorBot translatorBot() {
        return new TranslatorBot(telegramFacade);
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Locale.US);
        return localeResolver;
    }

    @Bean
    public ResourceBundleMessageSource resourceBundleMessageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        messageSource.setCacheSeconds(3600);
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean(name = "messagesKeys")
    public Map<String, BotState> messagesKeys() {
        Map<String, BotState> strings = new HashMap<>();
        strings.put("system.menu.kirill.to.uzbek", BotState.KRILL_TO_UZB);
        strings.put("system.menu.uzbek.to.kirill", BotState.UZB_TO_KRILL);
        strings.put("system.menu.settings", BotState.SETTINGS);
        return strings;
    }

/*    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setCacheSeconds(3600);
        messageSource.setDefaultEncoding("ISO-8859-1"); // Add this
        return messageSource;
    }*/
}
