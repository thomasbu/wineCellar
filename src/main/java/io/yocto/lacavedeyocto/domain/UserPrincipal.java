package io.yocto.lacavedeyocto.domain;

import io.yocto.lacavedeyocto.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

import static io.yocto.lacavedeyocto.dtomapper.UserDTOMapper.fromUser;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
public class UserPrincipal implements UserDetails {
    private final User user;
    private final String permissions;
//    private final Role role;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return stream(permissions.split(",".trim())).map(SimpleGrantedAuthority::new).collect(toList());
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.user.isNotLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.user.isEnabled();
    }
//    @Override
//    public UserDTO getUser() {
//        return fromUser(user, role);
//    }
}
