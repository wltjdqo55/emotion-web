package suggest.emotion_web.model.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserVO {

  private long id;

  private String userId;

  private String userPassword;

  private String userName;

  public UserVO(String userId, String userName) {
    this.userId = userId;
    this.userName = userName;
  }
}
