package model.weather.Security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomUserDetails extends User {
    private String userId;

    public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities, String string) {
        super(username, password, authorities);
        this.userId = string;
    }

    public String getUserId() {
        return userId;
    }
}

