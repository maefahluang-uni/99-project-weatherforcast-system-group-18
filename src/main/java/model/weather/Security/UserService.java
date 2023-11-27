package model.weather.Security;

import javax.transaction.Transactional;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
@Service
public interface UserService extends UserDetailsService {
    void save(User newUser);
}


