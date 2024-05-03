package shkond.server.model.users;

import jakarta.persistence.*;
import shkond.server.model.enums.EnumRole;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_name", length = 13)
    private EnumRole role;

    public Role(){}     //Необходимость Великая!
    public Role(EnumRole role) {
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public EnumRole getRole() {
        return role;
    }

    public void setRole(EnumRole role) {
        this.role = role;
    }
}
