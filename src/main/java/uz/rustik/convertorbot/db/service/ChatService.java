package uz.rustik.convertorbot.db.service;

import org.springframework.stereotype.Service;
import uz.rustik.convertorbot.db.entity.Chat;
import uz.rustik.convertorbot.db.entity.enums.BotState;
import uz.rustik.convertorbot.db.ropository.ChatRepository;

import java.util.Optional;

@Service
public class ChatService {
    private final ChatRepository chatRepository;

    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public Chat saveOrUpdate(Chat chat) {
        return chatRepository.save(chat);
    }

    public Chat findByChatId(Long chatId) {
        Optional<Chat> chatOptional = chatRepository.findById(chatId);
        return chatOptional.orElse(null);
    }

    public Chat setChatStateByChatId(Long chatId, BotState botState) {
        Chat chat = findByChatId(chatId);

        if (chat == null) {
            chat = new Chat(chatId);
        }

        chat.setBotState(botState);

        return saveOrUpdate(chat);
    }


}
