package model.weather.Model;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import model.weather.Security.User;

@Repository
public interface LocationRepository extends CrudRepository<Location, Long> {
    List<Location> findByLatitudeAndLongitude(Double latitude, Double longitude);
    List<Location> findByUserid(Long userid);
}
