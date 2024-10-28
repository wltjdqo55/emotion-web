package suggest.taste_the_weather.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import suggest.taste_the_weather.model.entity.QUser;
import suggest.taste_the_weather.model.entity.User;

@Repository
@RequiredArgsConstructor
public class AccountQueryDSL {

  private final JPAQueryFactory jpaQueryFactory;
  QUser user = QUser.user;

  public User findUserInfo (String userId) {
    return jpaQueryFactory
        .select(user)
        .from(user)
        .where(
            user.userId.eq(userId)
        )
        .fetchOne();
  }
}
