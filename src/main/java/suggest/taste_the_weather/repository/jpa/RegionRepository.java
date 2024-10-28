package suggest.taste_the_weather.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import suggest.taste_the_weather.model.entity.Region;

import java.util.List;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {

  @Query(value = "SELECT region_parent, region_child FROM region_list WHERE nx = :nx AND ny = :ny LIMIT 1", nativeQuery = true)
  List<Object[]> findRegionLocation(@Param("nx") int nx, @Param("ny") int ny);
}
