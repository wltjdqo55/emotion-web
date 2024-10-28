package suggest.taste_the_weather.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import suggest.taste_the_weather.model.dto.UserDTO;
import suggest.taste_the_weather.model.entity.User;
import suggest.taste_the_weather.model.vo.UserVO;
import suggest.taste_the_weather.repository.jpa.AccountRepository;
import suggest.taste_the_weather.repository.querydsl.AccountQueryDSL;

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
