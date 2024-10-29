package suggest.taste_the_weather.api.weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import suggest.taste_the_weather.model.dto.WeatherDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WeatherData {

  private String baseDate = calculateBaseDate();
  private String baseTime = calculateBaseTime();
  private String type = "json";	//조회하고 싶은 type( json, xml 중 고름 )

  public JSONObject lookUpWeather(int x, int y, String address) throws IOException, JSONException {
    System.out.println(address);
//		참고문서에 있는 url주소
    String apiUrl = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst";
//    홈페이지에서 받은 키 ( 공공 데이터 포털 )
    String serviceKey = "BSfQt0ifiEuIu2Ms%2BqzwkXCn6eMPnhckqcU9fvN7Z8shVRJTH2Cbm1DHseqaYXD4d0En7PZkbVBMCF%2BWQay6Ug%3D%3D";

    StringBuilder urlBuilder = new StringBuilder(apiUrl);
    urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "="+serviceKey);
    urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode(String.valueOf(x), "UTF-8")); //경도
    urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode(String.valueOf(y), "UTF-8")); //위도
    urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode(baseDate, "UTF-8")); /* 조회하고싶은 날짜*/
    urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode(baseTime, "UTF-8")); /* 조회하고싶은 시간 AM 02시부터 3시간 단위 */
    urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode(type, "UTF-8"));	/* 타입 */

    /*
     * GET방식으로 전송해서 파라미터 받아오기
     */
    URL url = new URL(urlBuilder.toString());

    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("GET");
    conn.setRequestProperty("Content-type", "application/json");
//    System.out.println("Response code: " + conn.getResponseCode());

    BufferedReader rd;
    if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
      rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    } else {
      rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
    }

    StringBuilder sb = new StringBuilder();
    String line;
    while ((line = rd.readLine()) != null) {
      sb.append(line);
    }

    rd.close();
    conn.disconnect();
    String result= sb.toString();

    //=======이 밑에 부터는 json에서 데이터 파싱해 오는 부분=====//
//    System.out.println(result);

    List<WeatherDTO> weatherDataList = new ArrayList<>();

    JSONObject jsonObject = new JSONObject(result);
//    System.out.println(jsonObject);
    JSONObject response = jsonObject.getJSONObject("response");
    JSONObject body = response.getJSONObject("body");
    JSONArray items = body.getJSONObject("items").getJSONArray("item");


    for(int i = 0 ; i < items.length(); i++) {
      JSONObject item = items.getJSONObject(i);  // JSON 객체를 가져옴
      String baseDate = item.getString("baseDate");
      String baseTime = item.getString("baseTime");
      String category = item.getString("category");
      String obsrValue = item.getString("obsrValue");
      int nx = item.getInt("nx");
      int ny = item.getInt("ny");

      WeatherDTO weatherDTO = new WeatherDTO(baseDate, baseTime, category, obsrValue, nx, ny, address);
      weatherDataList.add(weatherDTO);
    }
    String time = currentTime(weatherDataList);
    HashMap<String, String> map = weatherInfoText(weatherDataList);
    return createResponse(time, map, address);
  }

  public static String calculateBaseDate() {
    return LocalDateTime.now().minusHours(1).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
  }

  public static String calculateBaseTime() {
    return LocalDateTime.now().minusHours(1).format(DateTimeFormatter.ofPattern("HHmm"));
  }

  public static String currentTime(List<WeatherDTO> list) {  // 조회시간 문자열로 생성
    String date = list.get(0).getBaseDate();
    String time = list.get(0).getBaseTime();
    String s = "조회시간 : ";
    s += date.substring(0, 4) + "년 " + date.substring(4,6) + "월 " + date.substring(6) + "일 ";
    s += time.substring(0, 2) + "시 " + time.substring(2) + "분";

    return s;
  }

  public static HashMap<String, String> weatherInfoText(List<WeatherDTO> list) {

    HashMap<String, String> map = new HashMap<>();

    for( WeatherDTO weather : list ) {
      String category = weather.getCategory();
      String obsrValue = weather.getObsrValue();

      if ( category.equals("PTY") ) {
        if ( obsrValue.equals("0") ) {
          map.put("precipitation", "normal");     // 적당한 날
        } else {
          map.put("precipitation", "rain");        // 비오는 날
        }
      } else if ( category.equals("T1H") ) {
        double d = Double.parseDouble(obsrValue);
        if ( d > 29.0f ) {
          map.put("temperature", "hot");  // 더움
        } else if ( d < 5.0f ) {
          map.put("temperature", "cold"); // 추움
        } else {
          map.put("temperature", "moderation"); // 적당한 날
        }
      }
    }
    return map;
  }

  public JSONObject createResponse(String time, HashMap<String, String> map, String address) {
    // JSON 객체 생성
    JSONObject response = new JSONObject();

    response.put("weatherInfo", new JSONObject(map)); // HashMap을 JSON으로 변환
    response.put("address", address);

    return response;
  }
}
