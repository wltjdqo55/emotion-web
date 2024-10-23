package suggest.emotion_web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import suggest.emotion_web.model.dto.UserDTO;
import suggest.emotion_web.service.NaverLoginService;
import suggest.emotion_web.session.UserSession;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

@Controller
@RequiredArgsConstructor
public class LoginController {

  private final NaverLoginService naverloginService;

  //네이버 로그인
  @GetMapping("/naverLogin")
  public void naverLogin(HttpServletResponse response) throws MalformedURLException, UnsupportedEncodingException {
    String naverAuthorizeUrl = naverloginService.getNaverAuthorizeUrl();
    try {
      response.sendRedirect(naverAuthorizeUrl); // Naver 인증 URL로 리다이렉트
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  //네이버 로그인시 콜백 URL
  @GetMapping("/naver/login/callback")
  public String naverCallback(@RequestParam("code") String code, @RequestParam("state") String state, HttpSession session) throws JsonProcessingException {
    System.out.println("code=>" + code);
    System.out.println("state=>" + state);

    UserDTO userDTO = naverloginService.naverGetToken(code);    // 네이버 토큰 발급

    if(userDTO != null) {
      UserSession.setSession(userDTO, session, 3600);
    }

    return "redirect:/main"; // 콜백 후 보여줄 페이지
  }
}
