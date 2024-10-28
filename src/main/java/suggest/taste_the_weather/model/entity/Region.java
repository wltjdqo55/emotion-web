package suggest.taste_the_weather.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name="region_list")
public class Region {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String regionParent;

  private String regionChild;

  private int nx;

  private int ny;



}
