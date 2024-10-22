package suggest.emotion_web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import suggest.emotion_web.model.dto.UserDTO;
import suggest.emotion_web.model.entity.User;
import suggest.emotion_web.model.vo.UserVO;
import suggest.emotion_web.repository.jpa.AccountRepository;

@Service
@RequiredArgsConstructor
public class AccountService {

  private final AccountRepository accountRepository;

  public boolean idCheck (String userId) {
    return accountRepository.findOneByUserId(userId).isEmpty();
  }

  public UserDTO userAdd (UserVO userVO) {
    return new UserDTO(accountRepository.save(new User(userVO)));
  }

}
