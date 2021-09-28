package uz.rustik.convertorbot.bot.message_hendler.handler;

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
import java.util.List;

@Component
public class MainMenuMessageHandler implements InputMessageHandlerInterface {
    private final ChatService chatService;
    private final MessagesService messagesService;

    public MainMenuMessageHandler(ChatService chatService, MessagesService messagesService) {
        this.chatService = chatService;
        this.messagesService = messagesService;
    }

    @Override
    public BotApiMethod<?> handle(Message message) {

        Chat chat = chatService.findByChatId(message.getChatId());

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chat.getId().toString());

        if (message.hasText()) {
            String messageText = message.getText();
            if (messageText.equals(messagesService.getMessage("system.menu.kirill.to.uzbek", chat.getLocale()))) {
                sendMessage.setText(messagesService.getMessage("message.kirill.to.uzbek", chat.getLocale()));

                chat.setBotState(BotState.KRILL_TO_UZB_HANDLER);
                chatService.saveOrUpdate(chat);

                return sendMessage;
            }

            if (messageText.equals(messagesService.getMessage("system.menu.uzbek.to.kirill", chat.getLocale()))) {
                sendMessage.setText(messagesService.getMessage("message.uzbek.to.kirill", chat.getLocale()));

                chat.setBotState(BotState.UZB_TO_KRILL_HANDLER);
                chatService.saveOrUpdate(chat);

                return sendMessage;
            }

            if (messageText.equals(messagesService.getMessage("system.menu.settings", chat.getLocale()))) {
                sendMessage.setText(messagesService.getMessage("settings", chat.getLocale()));

                chat.setBotState(BotState.MAIN_MENU);
                chatService.saveOrUpdate(chat);

                return sendMessage;
            }
        }

        sendMessage.setText(messagesService.getMessage("message.main.menu", chat.getLocale()));
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        row1.add(messagesService.getMessage("system.menu.kirill.to.uzbek", chat.getLocale()));
        KeyboardRow row2 = new KeyboardRow();
        row2.add(messagesService.getMessage("system.menu.uzbek.to.kirill", chat.getLocale()));

        KeyboardRow row3 = new KeyboardRow();
        row3.add(messagesService.getMessage("system.menu.settings", chat.getLocale()));

        keyboardRows.add(row1);
        keyboardRows.add(row2);
        keyboardRows.add(row3);
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        return sendMessage;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.MAIN_MENU;
    }

    @Override
    public BotState backState(Message message) {
        return BotState.MAIN_MENU;
    }
}
