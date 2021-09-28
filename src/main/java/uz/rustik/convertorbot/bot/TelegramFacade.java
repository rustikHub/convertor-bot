package uz.rustik.convertorbot.bot;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import uz.rustik.convertorbot.bot.call_back_query.CallBackQueryFacade;
import uz.rustik.convertorbot.db.entity.Chat;
import uz.rustik.convertorbot.db.entity.enums.BotState;
import uz.rustik.convertorbot.db.service.ChatService;
import uz.rustik.convertorbot.service.MessagesService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TelegramFacade {

    private final ChatService chatService;
    private final BotStateContext botStateContext;
    private final CallBackQueryFacade callBackQueryFacade;
    private final Map<String, BotState> messagesKeys;
    private final MessagesService messagesService;


    public TelegramFacade(ChatService chatService, @Lazy BotStateContext botStateContext, CallBackQueryFacade callBackQueryFacade, @Qualifier("messagesKeys") Map<String, BotState> messagesKeys, MessagesService messagesService) {
        this.chatService = chatService;
        this.botStateContext = botStateContext;
        this.callBackQueryFacade = callBackQueryFacade;
        this.messagesKeys = messagesKeys;
        this.messagesService = messagesService;
    }

    public BotApiMethod<?> handleUpdate(Update update) {
        BotApiMethod<?> sendMessage = null;

        if (update.hasCallbackQuery()) {
            sendMessage = callBackQueryFacade.processCallbackQuery(update.getCallbackQuery());
        }

        Message message = update.getMessage();

        if (message != null && (message.hasText() || message.hasDocument())) {
            sendMessage = handleInputMessage(message);
        }

        return sendMessage;
    }

    private BotApiMethod<?> handleInputMessage(Message message) {
        String inputMsg = "";
        Chat chat = chatService.findByChatId(message.getChatId());

        boolean isNew = false;

        if (chat == null) {
            isNew = true;
            chat = chatService.setChatStateByChatId(message.getChatId(), BotState.START);
        }
        BotState currentBotState = chat.getBotState();

        if (message.hasDocument()) {
            return botStateContext.processInputMessage(currentBotState, message);
        }

        if (message.hasText()) {
            inputMsg = message.getText();
        }

        List<String> array = new ArrayList<>(messagesKeys.keySet());

        for (String s : array) {
            if (messagesService.getMessage(s, chat.getLocale()).equals(inputMsg)) {
                BotState botState = messagesKeys.get(s);
                chatService.setChatStateByChatId(message.getChatId(), botState);
                return botStateContext.processInputMessage(botState, message);
            }
        }

        if (inputMsg.equals(messagesService.getMessage("system.menu.back", chat.getLocale()))) {
            currentBotState = botStateContext.getHandlerBackState(currentBotState, message);
        }

        switch (inputMsg) {
            case "/back": {
                currentBotState = botStateContext.getHandlerBackState(currentBotState, message);
                break;
            }

            case "/start": {
                if (isNew) {
                    currentBotState = BotState.CHANGE_LANGUAGE;
                } else {
                    currentBotState = BotState.MAIN_MENU;
                }
                break;
            }
        }
        chat = chatService.setChatStateByChatId(message.getChatId(), currentBotState);

        return botStateContext.processInputMessage(chat.getBotState(), message);

    }


}
