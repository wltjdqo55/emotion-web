package suggest.emotion_web.repository.querydsl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import suggest.emotion_web.model.dto.UserDTO;
import suggest.emotion_web.model.entity.QUser;
import suggest.emotion_web.model.entity.User;
import suggest.emotion_web.model.vo.UserVO;

@Repository
@RequiredArgsConstructor
public class AccountQueryDSL {

  private final JPAQueryFactory jpaQueryFactory;
  QUser user = QUser.user;

  public User loginCheck (UserVO userVO) {

    return jpaQueryFactory
        .select(user)
        .from(user)
        .where(
            user.userId.eq(userVO.getUserId()).and(user.userPassword.eq(userVO.getUserPassword()))
        )
        .fetchOne();
  }

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
