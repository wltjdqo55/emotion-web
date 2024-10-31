package suggest.taste_the_weather.model.dto.naver;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;

@Data
@NoArgsConstructor
public class NaverSearchDTO {

  private String lastBuildDate;
  private int total;
  private int start;
  private int display;

  private String title;
  private String link;
  private String category;
  private String description;
  private String telephone;
  private String address;
  private String roadAddress;
  private String mapx;
  private String mapy;

  //날씨정보
  private String temperature;
  private String precipitation;
  private String weatherDate;

  public NaverSearchDTO (String title, String link, String category, String description, String telephone,
                         String address, String roadAddress, String mapx, String mapy, String temperature, String precipitation, String weatherDate) {
    this.title = title;
    this.link = link;
    this.category = category;
    this.description = description;
    this.telephone = telephone;
    this.address = address;
    this.roadAddress = roadAddress;
    this.mapx = mapx;
    this.mapy = mapy;
    this.temperature = temperature;
    this.precipitation = precipitation;
    this.weatherDate = weatherDate;
  }
}
