package shkond.server.model.users;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import shkond.server.model.enums.EnumRole;

@Entity
@Getter
@Setter
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer id;
    /* указывает, что поле будет отображаться как строка в БД */
    @Enumerated(EnumType.STRING)
    @Column(name = "role_name", length = 13)
    private EnumRole role;
    public Role(){}     //Необходимость Великая!
    public Role(EnumRole role) {
        this.role = role;
    }
}
