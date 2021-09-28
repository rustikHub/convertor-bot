package uz.rustik.convertorbot.bot.message_hendler.handler;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import uz.rustik.convertorbot.bot.call_back_query.CallbackQueryType;
import uz.rustik.convertorbot.bot.message_hendler.InputMessageHandlerInterface;
import uz.rustik.convertorbot.db.entity.Chat;
import uz.rustik.convertorbot.db.entity.enums.BotState;
import uz.rustik.convertorbot.db.service.ChatService;
import uz.rustik.convertorbot.service.MessagesService;
import uz.rustik.convertorbot.utils.Emojis;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
public class StartMessageHandler implements InputMessageHandlerInterface {


    private final MessagesService messagesService;
    private final ChatService chatService;

    public StartMessageHandler(MessagesService messagesService, ChatService chatService) {
        this.messagesService = messagesService;
        this.chatService = chatService;
    }

    @Override
    public BotApiMethod<?> handle(Message message) {

        Chat chat = chatService.findByChatId(message.getChatId());

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chat.getId().toString());
        Locale locale = chat.getLocale();

        String cache = chat.getCache();

        sendMessage.setText(String.format(messagesService.getMessage("message.change.language", locale)," "+ getCurrentLocaleText(locale)));

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboardList = new ArrayList<>();

        List<InlineKeyboardButton> column = new ArrayList<>();
        InlineKeyboardButton english = new InlineKeyboardButton("\uD83C\uDDFA\uD83C\uDDF8 English \uD83C\uDDFA\uD83C\uDDF8");
        english.setCallbackData(CallbackQueryType.CHANGE_LANGUAGE + ":en");
        column.add(english);
        keyboardList.add(column);

        column = new ArrayList<>();
        InlineKeyboardButton russian = new InlineKeyboardButton("\uD83C\uDDF7\uD83C\uDDFA Russian \uD83C\uDDF7\uD83C\uDDFA");
        russian.setCallbackData(CallbackQueryType.CHANGE_LANGUAGE + ":ru");
        column.add(russian);
        keyboardList.add(column);

        column = new ArrayList<>();
        InlineKeyboardButton uzbek = new InlineKeyboardButton("\uD83C\uDDFA\uD83C\uDDFF Uzbek \uD83C\uDDFA\uD83C\uDDFF");
        uzbek.setCallbackData(CallbackQueryType.CHANGE_LANGUAGE + ":uz");
        column.add(uzbek);
        keyboardList.add(column);

        inlineKeyboardMarkup.setKeyboard(keyboardList);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);

        return sendMessage;
    }

    private String getCurrentLocaleText(Locale locale) {
        switch (locale.getLanguage()) {
            case "uz":
                return Emojis.FLAG_UZ.toString();
            case "ru":
                return Emojis.FLAG_RU.toString();

            case "us":
            default:
                return Emojis.FLAG_US.toString();
        }
    }

    @Override
    public BotState getHandlerName() {
        return BotState.CHANGE_LANGUAGE;
    }

    @Override
    public BotState backState(Message message) {
        return BotState.CHANGE_LANGUAGE;
    }
}
