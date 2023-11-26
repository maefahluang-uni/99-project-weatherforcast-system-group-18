package model.weather.Model;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends CrudRepository<Location, Long> {

    List<Location> findByLatitudeAndLongitude(Double latitude, Double longitude);
    // You can add query methods if you need to find locations by certain criteria

}
