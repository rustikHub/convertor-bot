package uz.rustik.convertorbot.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import uz.rustik.convertorbot.bot.TranslatorBot;

@RestController
@RequestMapping("bot")
public class BotController {

    private final TranslatorBot translatorBot;

    public BotController(TranslatorBot translatorBot) {
        this.translatorBot = translatorBot;
    }

    @PostMapping("test")
    public BotApiMethod<?> getUpdate(@RequestBody Update update) {
        return translatorBot.onWebhookUpdateReceived(update);
    }
}
