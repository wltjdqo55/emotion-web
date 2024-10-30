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
  private List<RestaurantDTO> items; // Restaurant 객체 리스트

  public NaverSearchDTO (String lastBuildDate, int total, int start, int display, List<RestaurantDTO> items) {
    this.lastBuildDate = lastBuildDate;
    this.total = total;
    this.start = start;
    this.display = display;
    this.items = items;
  }
}
