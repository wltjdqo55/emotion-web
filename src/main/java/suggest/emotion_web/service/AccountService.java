package suggest.emotion_web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import suggest.emotion_web.model.dto.UserDTO;
import suggest.emotion_web.model.entity.User;
import suggest.emotion_web.model.vo.UserVO;
import suggest.emotion_web.repository.jpa.AccountRepository;
import suggest.emotion_web.repository.querydsl.AccountQueryDSL;

@Service
@RequiredArgsConstructor
public class AccountService {

  private final AccountRepository accountRepository;
  private final AccountQueryDSL accountQueryDSL;
  private final BCryptPasswordEncoder encoder;

  public boolean idCheck (String userId) {
    return accountRepository.findOneByUserId(userId).isEmpty();
  }

  @Transactional
  public UserDTO userAdd (UserVO userVO) {
    userVO.setUserPassword(encoder.encode(userVO.getUserPassword()));
    return new UserDTO(accountRepository.save(new User(userVO)));
  }

  public UserDTO loginCheck (UserVO userVO) {
    User user = accountRepository.findOneByUserId(userVO.getUserId()).orElse(new User());
    if(encoder.matches(userVO.getUserPassword(), user.getUserPassword())){
      return new UserDTO(user);
    }
    return null;
  }

}
