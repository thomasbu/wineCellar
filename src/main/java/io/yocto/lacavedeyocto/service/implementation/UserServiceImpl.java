package io.yocto.lacavedeyocto.service.implementation;

import io.yocto.lacavedeyocto.domain.Role;
import io.yocto.lacavedeyocto.domain.User;
import io.yocto.lacavedeyocto.dto.UserDTO;
import io.yocto.lacavedeyocto.repository.RoleRepository;
import io.yocto.lacavedeyocto.repository.UserRepository;
import io.yocto.lacavedeyocto.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static io.yocto.lacavedeyocto.dtomapper.UserDTOMapper.fromUser;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository<User> userRepository;
    private final RoleRepository<Role> roleRepository;
    @Override
    public UserDTO createUser(User user) {
        return mapToUserDTO(userRepository.create(user));
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        return mapToUserDTO(userRepository.getUserByEmail(email));
    }

    private UserDTO mapToUserDTO(User user) {
        return fromUser(user, roleRepository.getRoleByUserId(user.getId()));
    }
}
