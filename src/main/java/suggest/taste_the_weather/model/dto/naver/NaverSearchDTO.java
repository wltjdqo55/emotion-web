package suggest.taste_the_weather.model.dto.naver;

import lombok.Data;
import lombok.NoArgsConstructor;

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

  public NaverSearchDTO (String title, String link, String category, String description, String telephone, String address, String roadAddress, String mapx, String mapy) {
    this.title = title;
    this.link = link;
    this.category = category;
    this.description = description;
    this.telephone = telephone;
    this.address = address;
    this.roadAddress = roadAddress;
    this.mapx = mapx;
    this.mapy = mapy;
  }
}
