package uz.rustik.convertorbot.db.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import uz.rustik.convertorbot.db.entity.enums.MessageType;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Message {
    @Id
    Long chatId;

    Integer messageId;

    MessageType messageType = MessageType.NOT_IMPORTANT;

    public Message(Long chatId, Integer messageId) {
        this.chatId = chatId;
        this.messageId = messageId;
    }
}
