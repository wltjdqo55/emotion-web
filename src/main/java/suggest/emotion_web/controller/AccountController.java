package suggest.emotion_web.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import suggest.emotion_web.model.dto.UserDTO;
import suggest.emotion_web.model.vo.UserVO;
import suggest.emotion_web.service.AccountService;
import suggest.emotion_web.session.UserSession;

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

  @PostMapping("/account/loginCheck")
  @ResponseBody
  public boolean loginCheck (UserVO userVO, HttpSession session) {
    System.out.println(userVO);
    UserDTO userDTO = accountService.loginCheck(userVO);
    if(userDTO != null) {
      UserSession.setSession(userDTO, session, 3600);
      return true;
    }
    return false;
  }

  @GetMapping("/account/getUserInfo")
  @ResponseBody
  public UserDTO getUserInfo (Model model, HttpSession session) {
    if( session.getAttribute("userInfo") != null) {
      return (UserDTO) session.getAttribute("userInfo");
    }
    return null;
  }

  @GetMapping("/account/sessionCheck")
  @ResponseBody
  public boolean sessionCheck(HttpSession session) {
    return session.getAttribute("userInfo") != null;
  }

  @GetMapping("/account/logout")
  @ResponseBody
  public boolean logout(HttpSession session) {
    UserSession.removeSession("userInfo", session);
    return !sessionCheck(session);
  }
}
