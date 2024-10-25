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

  private String connect;

  public UserVO(String userId, String userName, String connect) {
    this.userId = userId;
    this.userName = userName;
    this.connect = connect;
  }
}
