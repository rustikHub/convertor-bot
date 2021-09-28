package uz.rustik.convertorbot.bot;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import uz.rustik.convertorbot.bot.message_hendler.InputMessageHandlerInterface;
import uz.rustik.convertorbot.db.entity.enums.BotState;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BotStateContext {

    private final Map<BotState, InputMessageHandlerInterface> messageHandlers;

    public BotStateContext(@NotNull List<InputMessageHandlerInterface> inputMessageHandlers) {
        messageHandlers = new HashMap<>();
        inputMessageHandlers.forEach(it -> messageHandlers.put(it.getHandlerName(), it));
    }

    public BotApiMethod<?> processInputMessage(BotState botState, Message message) {

        if (isMainMenuState(botState)) {
            botState = BotState.MAIN_MENU;
        }

        if (isMessageHandlerState(botState)) {
            botState = BotState.MESSAGE_HANDLER;
        }

        return messageHandlers.get(botState).handle(message);
    }

    private boolean isMessageHandlerState(BotState botState) {
        switch (botState) {
            case MESSAGE_HANDLER:
            case UZB_TO_KRILL_HANDLER:
            case KRILL_TO_UZB_HANDLER:
                return true;
            default:
                return false;
        }
    }

    private boolean isMainMenuState(BotState botState) {
        switch (botState) {
            case MAIN_MENU:
            case UZB_TO_KRILL:
            case KRILL_TO_UZB:
                return true;
            default:
                return false;
        }
    }

    public BotState getHandlerBackState(BotState botState, Message message) {
        return messageHandlers.get(botState).backState(message);
    }
}
