package uz.rustik.convertorbot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import uz.rustik.convertorbot.db.entity.enums.MessageType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HandlerMethodAnswer {
    private BotApiMethod<?> botApiMethod;
    private MessageType messageType;
}
