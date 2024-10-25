package suggest.emotion_web.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import suggest.emotion_web.model.vo.UserVO;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "\"user\"") // 테이블 이름에 큰따옴표 사용
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name ="user_id")
  private String userId;

  private String userPassword;

  private String userName;

  private LocalDateTime createdDate;

  private String connect;

  public User (UserVO userVO) {
    this.userId = userVO.getUserId();
    this.userPassword = userVO.getUserPassword();
    this.userName = userVO.getUserName();
    this.createdDate = LocalDateTime.now();
    this.connect = userVO.getConnect();
  }

}
