package br.com.fecaf.security;


import br.com.fecaf.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@RequiredArgsConstructor
public class JwtUserDetails implements UserDetails {

    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        String roleName  = user.getRole().getName();
        return List.of(new SimpleGrantedAuthority("ROLE_" + roleName));
    }

    @Override
    public String getPassword() {
        return user.getPasswordHash();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override public boolean isAccountNonExpired() {return true;}
    @Override public boolean isAccountNonLocked() {return true;}
    @Override public boolean isCredentialsNonExpired() {return true;}
    @Override public boolean isEnabled() {return true;}

    public User getUser() {
        return user;
    }
}
