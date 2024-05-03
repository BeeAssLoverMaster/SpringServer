package shkond.server.repository.users;

import org.springframework.data.jpa.repository.JpaRepository;
import shkond.server.model.enums.EnumRole;
import shkond.server.model.users.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByRole(EnumRole role);

}
