package uz.rustik.convertorbot.bot.message_hendler.handler;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import uz.rustik.convertorbot.bot.message_hendler.InputMessageHandlerInterface;
import uz.rustik.convertorbot.db.entity.Chat;
import uz.rustik.convertorbot.db.entity.enums.BotState;
import uz.rustik.convertorbot.db.service.ChatService;
import uz.rustik.convertorbot.service.MessagesService;

import java.util.ArrayList;
import java.util.Locale;

@Component
public class SettingsMessageHandler implements InputMessageHandlerInterface {

    private final ChatService chatService;
    private final MessagesService messagesService;
    private final StartMessageHandler startMessageHandler;

    public SettingsMessageHandler(ChatService chatService, MessagesService messagesService, @Lazy StartMessageHandler startMessageHandler) {
        this.chatService = chatService;
        this.messagesService = messagesService;
        this.startMessageHandler = startMessageHandler;
    }

    @Override
    public BotApiMethod<?> handle(Message message) {

        Chat chat = chatService.findByChatId(message.getChatId());

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());


        if (message.hasText()) {
            String inputText = message.getText();

            if (inputText.equals(messagesService.getMessage("system.menu.change.language", chat.getLocale()))) {

                chat.setCache(BotState.SETTINGS.toString());

                chatService.saveOrUpdate(chat);
                return startMessageHandler.handle(message);
            }

            if (inputText.equals(messagesService.getMessage("system.menu.info", chat.getLocale()))) {
                sendMessage.setText(messagesService.getMessage("message.info", chat.getLocale()));
                sendMessage.setReplyMarkup(getKeyboard(chat.getLocale()));
                sendMessage.enableHtml(true);
                return sendMessage;
            }
        }


        sendMessage.setReplyMarkup(getKeyboard(chat.getLocale()));
        sendMessage.setText(messagesService.getMessage("system.menu.settings", chat.getLocale()));
        return sendMessage;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.SETTINGS;
    }

    @Override
    public BotState backState(Message message) {
        return BotState.MAIN_MENU;
    }

    private ReplyKeyboardMarkup getKeyboard(Locale locale) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);

        ArrayList<KeyboardRow> column = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        row.add(messagesService.getMessage("system.menu.change.language", locale));
        column.add(row);

        row = new KeyboardRow();
        row.add(messagesService.getMessage("system.menu.info", locale));
        column.add(row);

        row = new KeyboardRow();
        row.add(messagesService.getMessage("system.menu.back", locale));
        column.add(row);

        replyKeyboardMarkup.setKeyboard(column);

        return replyKeyboardMarkup;
    }
}
