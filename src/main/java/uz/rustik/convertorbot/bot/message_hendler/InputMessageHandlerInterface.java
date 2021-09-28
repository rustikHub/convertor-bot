package uz.rustik.convertorbot.bot.message_hendler;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import uz.rustik.convertorbot.db.entity.enums.BotState;

public interface InputMessageHandlerInterface {
   public BotApiMethod<?> handle(Message message);

   public BotState getHandlerName();

   public BotState backState(Message message);
}
