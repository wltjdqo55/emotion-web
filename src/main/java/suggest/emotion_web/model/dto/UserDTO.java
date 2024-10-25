package suggest.emotion_web.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import suggest.emotion_web.model.entity.User;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class UserDTO {

  private long id;

  private String userId;

  private String userName;

  private LocalDateTime createdDate;

  private String connect;

  public UserDTO(User user) {
    this.id = user.getId();
    this.userId = user.getUserId();
    this.userName = user.getUserName();
    this.createdDate = user.getCreatedDate();
    if(user.getConnect()!=null) {
      this.connect = user.getConnect();
    }
  }
}
