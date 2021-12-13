package bogdan.kuzenko.shop.repository;

import bogdan.kuzenko.shop.dto.request.UserRequest;
import bogdan.kuzenko.shop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User save(UserRequest userRequest);
}
