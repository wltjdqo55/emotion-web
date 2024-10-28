package suggest.taste_the_weather.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import suggest.taste_the_weather.api.WeatherData;
import suggest.taste_the_weather.model.dto.WeatherDTO;
import suggest.taste_the_weather.model.vo.RegionVO;
import suggest.taste_the_weather.service.RegionService;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class RegionController {

  private final RegionService regionService;

  @GetMapping("/api/geoLocationApi")
  @ResponseBody
  public String geoLocationApi (RegionVO regionVO) throws IOException {
    String parent = "";
    String child = "";
    List<Object[]> results = regionService.getRegionValue(regionVO.getLatitude(), regionVO.getLongitude());
    if (!results.isEmpty()) {
      Object[] row = results.get(0);
      parent = String.valueOf(row[0].toString());
      child = String.valueOf(row[1].toString());
    }

    WeatherData weatherData = new WeatherData();
    List<WeatherDTO> weather = weatherData.lookUpWeather(regionVO.getLatitude(), regionVO.getLongitude(), parent + " " + child);

    return "";
  }
}
