package uz.rustik.convertorbot.db.entity;

import lombok.*;
import lombok.experimental.PackagePrivate;
import uz.rustik.convertorbot.db.entity.enums.BotState;

import javax.persistence.*;
import java.util.Locale;

@Entity
@Getter
@Setter
@PackagePrivate
@ToString
@Table(name = "chat")
@AllArgsConstructor
@NoArgsConstructor
public class Chat {
    @Id
    Long id;

    @Enumerated(EnumType.STRING)
    BotState botState = BotState.START;

    String cache;
    @Column(nullable = false)
    private String language = "us";

    public Locale getLocale() {
        return new Locale(language);
    }

    public Chat(Long id) {
        this.id = id;
    }
}
