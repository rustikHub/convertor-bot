package uz.rustik.convertorbot.bot.call_back_query.handler;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import uz.rustik.convertorbot.bot.BotStateContext;
import uz.rustik.convertorbot.bot.TranslatorBot;
import uz.rustik.convertorbot.bot.call_back_query.CallBackQueryHandler;
import uz.rustik.convertorbot.bot.call_back_query.CallbackQueryType;
import uz.rustik.convertorbot.db.entity.Chat;
import uz.rustik.convertorbot.db.entity.enums.BotState;
import uz.rustik.convertorbot.db.service.ChatService;
import uz.rustik.convertorbot.service.MessagesService;

import javax.validation.constraints.NotNull;
import java.util.Locale;

@Component
public class ChangeLanguageCallbackQueryHandler implements CallBackQueryHandler {


    private final ChatService chatService;
    private final TranslatorBot translatorBot;
    private final BotStateContext botStateContext;
    private final MessagesService messagesService;

    public ChangeLanguageCallbackQueryHandler(ChatService chatService, @Lazy TranslatorBot translatorBot, @Lazy BotStateContext botStateContext, MessagesService messagesService) {
        this.chatService = chatService;
        this.translatorBot = translatorBot;
        this.botStateContext = botStateContext;
        this.messagesService = messagesService;
    }

    @Override
    public BotApiMethod<?> handleCallbackQuery(CallbackQuery callbackQuery) {
        Message message = callbackQuery.getMessage();
        Chat chat = chatService.findByChatId(message.getChatId());
        String queryData = callbackQuery.getData();

        String language = queryData.substring(queryData.indexOf(":") + 1);

        chat = changeLanguage(chat, language);
        translatorBot.sendMessage(new SendMessage(message.getChatId().toString(),
                messagesService.getMessage("message.changed.language", chat.getLocale())));

        translatorBot.deleteMessage(new DeleteMessage(chat.getId().toString(), message.getMessageId()));

        if (chat.getCache() != null && !chat.getCache().isEmpty()) {
            try {
                BotState botState = BotState.valueOf(chat.getCache());
                chat.setCache("");
                chat.setBotState(botState);
                chatService.saveOrUpdate(chat);
                return botStateContext.processInputMessage(botState, message);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        chatService.setChatStateByChatId(chat.getId(), BotState.MAIN_MENU);
        return botStateContext.processInputMessage(BotState.MAIN_MENU, message);
    }

    @Override
    public CallbackQueryType getHandlerQueryType() {
        return CallbackQueryType.CHANGE_LANGUAGE;
    }

    private Chat changeLanguage(@NotNull Chat chat, String language) {
        Locale locale = new Locale(language);

        chat.setLanguage(locale.getLanguage());
        return chatService.saveOrUpdate(chat);
    }
}
