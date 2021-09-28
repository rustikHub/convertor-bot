package uz.rustik.convertorbot.db.ropository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.rustik.convertorbot.db.entity.Chat;

public interface ChatRepository extends JpaRepository<Chat, Long> {

}
