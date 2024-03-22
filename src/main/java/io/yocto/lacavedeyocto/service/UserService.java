package io.yocto.lacavedeyocto.service;

import io.yocto.lacavedeyocto.domain.User;
import io.yocto.lacavedeyocto.dto.UserDTO;

public interface UserService {
    UserDTO createUser(User user);
    UserDTO getUserByEmail(String email);

    UserDTO getUserById(Long userId);
}
