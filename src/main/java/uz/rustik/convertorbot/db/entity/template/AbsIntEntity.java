package uz.rustik.convertorbot.db.entity.template;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;


@MappedSuperclass
@Getter
@Setter
@ToString
public abstract class AbsIntEntity {
    @Column(nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


}
