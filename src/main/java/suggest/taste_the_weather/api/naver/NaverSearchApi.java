package suggest.taste_the_weather.api.naver;

import org.json.JSONArray;
import org.json.JSONObject;
import suggest.taste_the_weather.model.dto.naver.NaverSearchDTO;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

public class NaverSearchApi {

  String[] rainFoods = {                      // 비 오는 날 음식
      "부대찌개", "아구찜", "해물탕", "칼국수", "수제비", "짬뽕", "우동", "치킨", "국밥", "김치부침개","두부김치", "파전"
  };
  String[] hotFoods = {                       // 더운날 음식
      "김치말이국수", "도토리묵밥", "미숫가루", "오이냉국", "초계국수", "콩나물냉채", "냉면",
      "메밀소바", "콩국수", "물회", "삼계탕", "비빔국수", "팥빙수", "월남쌈"
  };
  String[] coldFoods = {                      // 추운날 음식
      "라멘", "마라탕", "곰탕", "부대찌개", "칼국수", "순대국", "감자탕", "김치찌개", "국밥", "전골", "우동", "해물탕",
      "샤브샤브", "김치볶음밥", "굴국밥", "짬뽕", "김밥", "갈비탕", "해장국"
  };

  String clientId = "dYzaofroFRhBPbIOcbMP";   // 클라이언트 키
  String clientSecret = "JfHqgTgeii";         // 시크릿 키
  int display = 0;                            // 한번에 표시할 검색 결과 개수
  int start = 1;                              // 검색 시작 위치
  String sort = "random";                    // 검색 정렬 방법 => comment : 리뷰순, random : 정확도순
  String query = "";                          // 네이버 검색어

  String address = "";                        // 사용자가 위치한 지역
  HashMap<String, String> map = new HashMap<>();  // 날씨 정보를 담은 map

  public ArrayList<NaverSearchDTO> searchLocationFood (JSONObject jsonData) {
    jsonParsing(jsonData);    // 데이터 파싱
    ArrayList<String> foodList = pickRandomFoods();

    System.out.println(foodList);

    ArrayList<NaverSearchDTO> naverSearchList = new ArrayList<>();
    if ( foodList == null ) {
      query = address + " 맛집";
      display = 3;

      System.out.println(query);
      String responseBody = requestURL();
      System.out.println(responseBody);

      naverSearchList.addAll(parseNaverSearchItems(responseBody));
    } else {
      for ( String food : foodList ) {
        query = address + " " + food + " 맛집";
        display = 1;

        System.out.println(query);
        String responseBody = requestURL();
        System.out.println(responseBody);
        naverSearchList.addAll(parseNaverSearchItems(responseBody));
      }
    }
    System.out.println("searchList=> " + naverSearchList);
    return naverSearchList;
  }

  private static String get(String apiUrl, Map<String, String> requestHeaders){
    HttpURLConnection con = connect(apiUrl);
    try {
      con.setRequestMethod("GET");
      for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
        con.setRequestProperty(header.getKey(), header.getValue());
      }


      int responseCode = con.getResponseCode();
      if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
        return readBody(con.getInputStream());
      } else { // 오류 발생
        return readBody(con.getErrorStream());
      }
    } catch (IOException e) {
      throw new RuntimeException("API 요청과 응답 실패", e);
    } finally {
      con.disconnect();
    }
  }


  private static HttpURLConnection connect(String apiUrl){
    try {
      URL url = new URL(apiUrl);
      return (HttpURLConnection)url.openConnection();
    } catch (MalformedURLException e) {
      throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
    } catch (IOException e) {
      throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
    }
  }


  private static String readBody(InputStream body){
    InputStreamReader streamReader = new InputStreamReader(body);

    try (BufferedReader lineReader = new BufferedReader(streamReader)) {
      StringBuilder responseBody = new StringBuilder();

      String line;
      while ((line = lineReader.readLine()) != null) {
        responseBody.append(line);
      }

      return responseBody.toString();
    } catch (IOException e) {
      throw new RuntimeException("API 응답을 읽는 데 실패했습니다.", e);
    }
  }

  private void jsonParsing ( JSONObject jsonData) {
    System.out.println(jsonData);
    address = jsonData.getString("address");
    JSONObject weatherInfoJson = jsonData.getJSONObject("weatherInfo");
    map = new HashMap<>();

    for ( String key : weatherInfoJson.keySet() ) {
      map.put(key, weatherInfoJson.getString(key));
    }
  }

  private ArrayList<String> pickRandomFoods () {
    ArrayList<String> list;
    list = getFoodList(rainFoods);
    if( map.containsKey("precipitation") ) {
      String precipitation = map.get( "precipitation" );
      String temperature = map.get("temperature");
      if( precipitation.equals("rain") ) {  // 비,눈,소나기
        list = getFoodList(rainFoods);
      } else {
        if ( temperature.equals("hot") ) {    //  더운날
          list = getFoodList(hotFoods);
        } else if ( temperature.equals("cold") ) {  // 추운날
          list = getFoodList(coldFoods);
        } else {                                    // 적당한 날
          list = null;
        }
      }
    }
    return list;
  }

  public ArrayList<String> getFoodList ( String[] foodArray ) {   // 랜덤 음식 5가지 생성

    HashSet<Integer> pickedIndices = new HashSet<>();
    Random random = new Random();
    ArrayList<String> list = new ArrayList<>();

    while ( pickedIndices.size() < 3 ) {
      int index = random.nextInt(foodArray.length);
      pickedIndices.add(index);
    }

    for ( int index : pickedIndices ) {
      list.add(foodArray[index]);
    }
    return list;
  }

  public String requestURL () {

    try {
      query = URLEncoder.encode(query, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException("검색어 인코딩 실패",e);
    }

    String apiURL = "https://openapi.naver.com/v1/search/local.json?sort=" + sort + "&display=" + display +
        "&start=" + start + "&query=" + query;          // api 호출 url
//    String apiURL = "https://openapi.naver.com/v1/search/blog?query=" + query;

    Map<String, String> requestHeaders = new HashMap<>();
    requestHeaders.put("X-Naver-Client-Id", clientId);
    requestHeaders.put("X-Naver-Client-Secret", clientSecret);

    return get(apiURL,requestHeaders);
  }

  // 공통된 JSON 파싱 로직
  private List<NaverSearchDTO> parseNaverSearchItems(String responseBody) {
    List<NaverSearchDTO> naverSearchList = new ArrayList<>();
    JSONObject jsonResponse = new JSONObject(responseBody);
    JSONArray items = jsonResponse.getJSONArray("items");

    for (int i = 0; i < items.length(); i++) {
      JSONObject item = items.getJSONObject(i);
      String title = item.getString("title");
      String link = item.getString("link");
      String category = item.getString("category");
      String description = item.getString("description");
      String telephone = item.getString("telephone");
      String address = item.getString("address");
      String roadAddress = item.getString("roadAddress");
      String mapx = item.getString("mapx");
      String mapy = item.getString("mapy");

      NaverSearchDTO naverSearchDTO = new NaverSearchDTO(title, link, category, description, telephone, address,
          roadAddress, mapx, mapy, map.get("temperature"), map.get("precipitation"), map.get("weatherDate"));
      naverSearchList.add(naverSearchDTO);
    }

    return naverSearchList;
  }

}
