package shkond.server.request.users;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdateUserRolesRequest {
    private Long userId;
    private List<Long> roleIds;
}