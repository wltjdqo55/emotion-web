package suggest.taste_the_weather.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import suggest.taste_the_weather.model.entity.User;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<User, Long> {

  Optional<User> findOneByUserId(String userId);
}
