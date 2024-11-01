package suggest.taste_the_weather.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import suggest.taste_the_weather.model.dto.UserDTO;
import suggest.taste_the_weather.service.KakaoLoginService;
import suggest.taste_the_weather.service.NaverLoginService;
import suggest.taste_the_weather.session.UserSession;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.Map;

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

    // 네이버 토큰 발급
    UserDTO userDTO = naverLoginService.naverGetToken(code);

    // 세션 등록
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
  public String kakaoCallback(@RequestParam("code") String code, HttpSession session) throws Exception {
    System.out.println("code=>" + code);

    // 토큰 받기
    String accessToken = kakaoLoginService.getAccessToken(code);

    // 사용자 정보 받기
    Map<String, Object> userInfo = kakaoLoginService.getUserInfo(accessToken);

    // 회원정보 가져오기
    UserDTO userDTO = kakaoLoginService.setUserInfo(String.valueOf(userInfo.get("id")), (String) userInfo.get("nickname"));
    userDTO.setToken(accessToken);
    // 세션 등록
    if(userDTO != null) {
      UserSession.setSession(userDTO, session, 3600);
    }

    return "redirect:/main";
  }
}
