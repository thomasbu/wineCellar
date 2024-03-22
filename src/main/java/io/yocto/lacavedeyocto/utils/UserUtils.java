package io.yocto.lacavedeyocto.utils;

import io.yocto.lacavedeyocto.domain.UserPrincipal;
import io.yocto.lacavedeyocto.dto.UserDTO;
import org.springframework.security.core.Authentication;

public class UserUtils {
    public static UserDTO getAuthenticatedUser(Authentication authentication) {
        System.out.println("getPrincipal()" + authentication.getPrincipal());
        return ((UserDTO) authentication.getPrincipal());
    }

    public static UserDTO getLoggedInUser(Authentication authentication) {
        return ((UserPrincipal) authentication.getPrincipal()).getUser();
    }
}
