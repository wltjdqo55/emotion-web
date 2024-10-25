package suggest.emotion_web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Service
@RequiredArgsConstructor
public class KakaoLoginService {

  String client_id = "a3a81da0530ddbe7ab244544fb7d71c8"; // 앱 키
  String response_type = "code";  // code로 고정

  public String getKakaoAuthorizeUrl () throws UnsupportedEncodingException {
    String redirect_uri = "http://localhost:8080/kakao/login/callback"; // 전달받을 서비스 서버의 URI
    // URL 인코딩
    String encodedRedirectUri = URLEncoder.encode(redirect_uri, "UTF-8");
    String encodedClientId = URLEncoder.encode(client_id, "UTF-8");

    String apiUrl = "https://kauth.kakao.com/oauth/authorize?response_type=" + response_type + "&client_id=" + encodedClientId
        + "&redirect_uri=" + encodedRedirectUri;
    
    return apiUrl; // 생성된 uri 반환
  }
}
