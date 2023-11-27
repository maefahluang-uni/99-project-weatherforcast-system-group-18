package model.weather.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import model.weather.Model.Location;
import model.weather.Model.LocationRepository;
import model.weather.Security.User;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    public Location saveCurrentLocation(Location location) {
        Location existingLocation = locationRepository.findById(1L).orElse(null);

        if (existingLocation != null) {
            existingLocation.setLatitude(location.getLatitude());
            existingLocation.setLongitude(location.getLongitude());
            existingLocation.setName(location.getName());
            return locationRepository.save(existingLocation);
        } else {
            location.setId(1L);
            return locationRepository.save(location);
        }
    }

    public Location getLocationById(Long id) {
        return locationRepository.findById(id).orElse(null);
    }

    public List<Location> getLocationsByUserid(Long userid) {
        return locationRepository.findByUserid(userid);
    }
}
