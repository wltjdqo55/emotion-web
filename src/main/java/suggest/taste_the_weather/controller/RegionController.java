package suggest.taste_the_weather.controller;

import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import suggest.taste_the_weather.api.naver.NaverSearchApi;
import suggest.taste_the_weather.api.weather.WeatherData;
import suggest.taste_the_weather.model.dto.naver.NaverSearchDTO;
import suggest.taste_the_weather.model.vo.RegionVO;
import suggest.taste_the_weather.service.RegionService;

import java.io.IOException;
import java.util.ArrayList;

@Controller
@RequiredArgsConstructor
public class RegionController {

  private final RegionService regionService;

  @GetMapping("/api/geoLocationApi")
  @ResponseBody
  public JSONObject geoLocationApi (RegionVO regionVO) throws IOException {

    WeatherData weatherData = new WeatherData();  // 날씨 데이터 가져오기

    JSONObject jsonData = weatherData.lookUpWeather(regionVO.getLatitude(), regionVO.getLongitude(), regionVO.getX(), regionVO.getY());

    System.out.println(jsonData);
    return jsonData;
  }

  @GetMapping("/api/searchLocationFood")
  @ResponseBody
  public ArrayList<NaverSearchDTO> searchLocationFood (RegionVO regionVO) throws  IOException {

    NaverSearchApi naverSearchApi = new NaverSearchApi();
    ArrayList<NaverSearchDTO> list = naverSearchApi.searchLocationFood(regionVO.getX(), regionVO.getY());

    return list;
  }
}
