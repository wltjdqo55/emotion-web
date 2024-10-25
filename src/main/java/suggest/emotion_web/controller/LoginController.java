package suggest.emotion_web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import suggest.emotion_web.model.dto.UserDTO;
import suggest.emotion_web.service.KakaoLoginService;
import suggest.emotion_web.service.NaverLoginService;
import suggest.emotion_web.session.UserSession;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

@Controller
@RequiredArgsConstructor
public class LoginController {

  private final NaverLoginService naverLoginService;
  private final KakaoLoginService kakaoLoginService;

  //네이버 로그인
  @GetMapping("/naverLogin")
  public void naverLogin(HttpServletResponse response) throws MalformedURLException, UnsupportedEncodingException {
    String naverAuthorizeUrl = naverLoginService.getNaverAuthorizeUrl();
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

    UserDTO userDTO = naverLoginService.naverGetToken(code);    // 네이버 토큰 발급

    if(userDTO != null) {
      UserSession.setSession(userDTO, session, 3600);
    }

    return "redirect:/main"; // 콜백 후 보여줄 페이지
  }

  //=============================================================//


  //카카오 로그인
  @GetMapping("/kakaoLogin")
  public void kakaoLogin(HttpServletResponse response) throws IOException, Exception {
    String kakaoAuthorizeUrl = kakaoLoginService.getKakaoAuthorizeUrl();
    try {
      response.sendRedirect(kakaoAuthorizeUrl);
    } catch (Exception e){
        e.printStackTrace();
    }
  }

  //카카오 로그인시 콜백 URL
  @GetMapping("/kakao/login/callback")
  public String kakaoCallback(@RequestParam("code") String code, HttpServletRequest request) throws JsonProcessingException{
    System.out.println("!!!!");
    System.out.println(code);
    // 나머지 처리 로직
    return "redirect:/main";
  }
}
