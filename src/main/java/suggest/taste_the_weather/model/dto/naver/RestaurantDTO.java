package suggest.taste_the_weather.model.dto.naver;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RestaurantDTO {

  private String title;
  private String link;
  private String category;
  private String description;
  private String telephone;
  private String address;
  private String roadAddress;
  private String mapx;
  private String mapy;

  public RestaurantDTO(String d) {  // 객체 담기

  }

}
