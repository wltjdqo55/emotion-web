package suggest.taste_the_weather.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import suggest.taste_the_weather.repository.jpa.RegionRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegionService {

  private final RegionRepository regionRepository;

  public List<Object[]> getRegionValue(int nx, int ny) {
    return regionRepository.findRegionLocation(nx, ny);
  }
}
