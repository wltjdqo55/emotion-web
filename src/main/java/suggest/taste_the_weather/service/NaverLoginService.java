package suggest.taste_the_weather.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import suggest.taste_the_weather.model.dto.naver.NaverTokenDTO;
import suggest.taste_the_weather.model.dto.UserDTO;
import suggest.taste_the_weather.model.entity.User;
import suggest.taste_the_weather.model.vo.UserVO;
import suggest.taste_the_weather.repository.jpa.AccountRepository;
import suggest.taste_the_weather.repository.querydsl.AccountQueryDSL;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NaverLoginService {

  private final AccountRepository accountRepository;
  private final AccountQueryDSL accountQueryDSL;

  String client_id = "dYzaofroFRhBPbIOcbMP";  // 발급키
  String client_secret = "JfHqgTgeii";  // 상태 토큰값
  NaverTokenDTO naverTokenDTO = new NaverTokenDTO();

  //네이버 로그인화면 요청
  public String getNaverAuthorizeUrl() throws MalformedURLException, UnsupportedEncodingException {
    String redirect_uri = "https://localhost:8443/naver/login/callback";
    // URL 인코딩
    String encodedRedirectUri = URLEncoder.encode(redirect_uri, "UTF-8");
    String encodedState = URLEncoder.encode(client_secret, "UTF-8");

    String apiUrl = "https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=" + client_id +
        "&redirect_uri=" + encodedRedirectUri + "&state=" + encodedState;

    return apiUrl; // 생성된 URL 반환
  }

  //로그인시 발급받은 토큰으로 회원 정보 가져오기
  public UserDTO naverGetToken(String code) throws JsonProcessingException {
    String apiUrl = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&client_id=" + client_id +
        "&client_secret=" + client_secret + "&code=" + code;

    try {
      JSONObject jsonResponse = getJsonObject(apiUrl);
      naverTokenDTO = new NaverTokenDTO(jsonResponse.getString("access_token"), jsonResponse.getString("refresh_token"),
          jsonResponse.getString("token_type"), jsonResponse.getString("expires_in"));

      return getUserProfile();   // 회원 정보 가져오기

    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  private static JSONObject getJsonObject(String apiUrl) throws IOException {
    URL url = new URL(apiUrl); // Naver 인증 URL로 리다이렉트
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("GET");
    conn.setRequestProperty("Accept", "application/json");  // JSON 형태로 받기

    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    StringBuilder response = new StringBuilder();
    String line;
    while((line = br.readLine()) != null) {
      response.append(line);
    }
    br.close();


    JSONObject jsonResponse = new JSONObject(response.toString());
    return jsonResponse;
  }

  private static String getResponseBody(String apiUrl, Map<String,String> requestHeaders) {
    HttpURLConnection con = connect(apiUrl);
    try {
      con.setRequestMethod("GET");
      for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
        con.setRequestProperty(header.getKey(), header.getValue());
      }


      int responseCode = con.getResponseCode();
      if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
        return readBody(con.getInputStream());
      } else { // 에러 발생
        return readBody(con.getErrorStream());
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    } finally {
      con.disconnect();
    }
  }

  private static HttpURLConnection connect(String apiUrl) {
    try {
      URL url = new URL(apiUrl);
      return (HttpURLConnection) url.openConnection();
    } catch (MalformedURLException e) {
      throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
    } catch (IOException e) {
      throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
    }
  }

  private static String readBody(InputStream body) {
    InputStreamReader streamReader = new InputStreamReader(body);

    try (BufferedReader lineReader = new BufferedReader(streamReader)) {
      StringBuilder responseBody = new StringBuilder();

      String line;
      while ((line = lineReader.readLine()) != null) {
        responseBody.append(line);
      }
      return responseBody.toString();
    } catch (IOException e) {
      throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
    }
  }

  //회원정보 가져오기
  public UserDTO getUserProfile () {

    String token = naverTokenDTO.getAccess_token();
    String header = "Bearer " + token;

    String apiUrl = "https://openapi.naver.com/v1/nid/me";

    Map<String, String> requestHeaders = new HashMap<>();
    requestHeaders.put("Authorization", header);
    String responseBody = getResponseBody(apiUrl, requestHeaders);

    System.out.println("responseBody=>" + responseBody);
    return setUserProfile(responseBody);
  }

  //DB에 저장 (존재하지 않을시)
  public UserDTO setUserProfile(String responseBody) {
    JSONObject jsonResponse  = new JSONObject(responseBody);
    JSONObject response = jsonResponse.getJSONObject("response");
    String userId = response.getString("id");
    String userName = unicodeStr(response.getString("name"));
    String connect = "naver";

    UserVO userVO = new UserVO(userId, userName, connect);

    if(accountRepository.findOneByUserId(userId).isEmpty()){  //가입된 정보가 없을 시 계정 새로 등록
      accountRepository.save(new User(userVO));
    }

    return new UserDTO(accountQueryDSL.findUserInfo(userId));

  }

  // 이름 유니코드 시퀀스 변환
  public String unicodeStr(String str) {
    StringBuilder result = new StringBuilder();
    String[] parts = str.split("\\\\u");

    for (String part : parts) {
      if (part.length() > 0) {
        // 16진수 부분을 처리하기 위한 부분
        if (part.length() >= 4) {
          int codePoint = Integer.parseInt(part.substring(0, 4), 16);
          result.append((char) codePoint);
          result.append(part.substring(4));
        } else {
          result.append(part);
        }
      }
    }

    return result.toString();
  }

}
