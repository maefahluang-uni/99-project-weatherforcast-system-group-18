package model.weather.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import model.weather.Model.Location;
import model.weather.Model.LocationRepository;

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

    
}
