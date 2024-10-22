package suggest.emotion_web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import suggest.emotion_web.model.dto.UserDTO;
import suggest.emotion_web.model.vo.UserVO;
import suggest.emotion_web.service.AccountService;

@Controller
@RequiredArgsConstructor
public class AccountController {

  private final AccountService accountService;

  @GetMapping("/join")
  public String join() {
    return "/account/join.html";
  }

  @GetMapping("/account/userIdCheck")
  @ResponseBody
  public boolean idCheck (UserVO userVO) {
    return accountService.idCheck(userVO.getUserId());
  }

  @PostMapping("/account/userAdd")
  @ResponseBody
  public UserDTO userAdd (UserVO userVO) {
    return accountService.userAdd(userVO);
  }
}
