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
import java.util.Map;

public class WeatherData {

  private String baseDate = calculateBaseDate();
  private String baseTime = calculateBaseTime();
  private String type = "json";	//조회하고 싶은 type( json, xml 중 고름 )

  public JSONObject lookUpWeather(int x, int y, String qn, String qy) throws IOException, JSONException {

    String address = toAddress(qn, qy);
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
    System.out.println(weatherDataList);
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
        map.put("temperature", obsrValue);
      }
    }
    map.put("weatherDate", list.get(0).getBaseDate());
    return map;
  }

  public JSONObject createResponse(String time, HashMap<String, String> map, String address) {
    // JSON 객체 생성
    JSONObject response = new JSONObject();

    response.put("weatherInfo", new JSONObject(map)); // HashMap을 JSON으로 변환
    response.put("address", address);

    return response;
  }

  public String toAddress(String x, String y) throws IOException, JSONException {

    JsonReader jsonReader = new JsonReader();

    // 홈페이지에서 받은 키 (국토교통부 - 디지털트윈국토)
    String apiKey = "2BDC8B43-9DE6-3157-810B-3E888EBC89A2";
    // 위도
    String latitude = x;
    // 경도
    String longitude = y;
    // api 테스트
    // # 파라미터 종류 확인 : http://www.vworld.kr/dev/v4dv_geocoderguide2_s002.do
    String reverseGeocodeURL = "http://api.vworld.kr/req/address?"
        + "service=address&request=getAddress&version=2.0&crs=epsg:4326&point="
        +  longitude + "," +  latitude
        + "&format=json"
        + "&type=both&zipcode=true"
        + "&simple=false&"
        + "key="+apiKey;
    String getJson = jsonReader.callURL(reverseGeocodeURL);
    System.out.println("getJson => " + getJson);
    Map<String, Object> map = jsonReader.string2Map(getJson);
    System.out.println("json => " + map.toString());

    // 지도 결과 확인하기
    ArrayList reverseGeocodeResultArr = (ArrayList) ((HashMap<String, Object>) map.get("response")).get("result");

    String parcel_address = "";
    String road_address = "";
    String address = "";

    for (int counter = 0; counter < reverseGeocodeResultArr.size(); counter++) {
      HashMap<String, Object> tmp = (HashMap<String, Object>) reverseGeocodeResultArr.get(counter);
      System.out.println("tmp = > " + tmp);
      String level0 = (String) ((HashMap<String, Object>) tmp.get("structure")).get("level0");
      String level1 = (String) ((HashMap<String, Object>) tmp.get("structure")).get("level1");
      String level2 = (String) ((HashMap<String, Object>) tmp.get("structure")).get("level2");
      //주소 : 도, 시, 구, 동
      address  = (String) tmp.get("text");

      if (tmp.get("type").equals("parcel")) {
        parcel_address = (String) tmp.get("text");
        parcel_address = parcel_address.replace(level0, "").replace(level1, "").replace(level2, "").trim();
      } else {
        road_address = "도로 주소:" + (String) tmp.get("text");
        road_address = road_address.replace(level0, "").replace(level1, "").replace(level2, "").trim();
      }
    }

    System.out.println("parcel_address = > " + parcel_address);
    System.out.println("road_address = > " + road_address);

    return address;
  }
}
