package uz.rustik.convertorbot.bot.call_back_query;


import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import uz.rustik.convertorbot.db.service.ChatService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class CallBackQueryFacade {

    private Map<CallbackQueryType, CallBackQueryHandler> handlersMap = new HashMap<>();
    private final ChatService chatService;


    public CallBackQueryFacade(List<CallBackQueryHandler> handlerList, ChatService chatService) {
        this.chatService = chatService;
        handlerList.forEach(handler -> handlersMap.put(handler.getHandlerQueryType(), handler));
    }

    public BotApiMethod<?> processCallbackQuery(CallbackQuery callbackQuery) {

        String queryData = callbackQuery.getData();

        CallbackQueryType queryType = CallbackQueryType.valueOf(queryData.substring(0, queryData.indexOf(":")));

        switch (queryType) {
            case CHANGE_LANGUAGE: {
                return handlersMap
                        .get(queryType)
                        .handleCallbackQuery(callbackQuery);
            }
        }

        return null;
    }
}
