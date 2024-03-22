package io.yocto.lacavedeyocto.service.implementation;

import io.yocto.lacavedeyocto.domain.Role;
import io.yocto.lacavedeyocto.domain.User;
import io.yocto.lacavedeyocto.dto.UserDTO;
import io.yocto.lacavedeyocto.repository.RoleRepository;
import io.yocto.lacavedeyocto.repository.UserRepository;
import io.yocto.lacavedeyocto.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static io.yocto.lacavedeyocto.dtomapper.UserDTOMapper.fromUser;

@Service
@RequiredArgsConstructor
@Slf4j
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

    @Override
    public UserDTO getUserById(Long userId) {
        log.info("getUserById");
        return mapToUserDTO(userRepository.get(userId));
    }

    private UserDTO mapToUserDTO(User user) {
        return fromUser(user, roleRepository.getRoleByUserId(user.getId()));
    }
}
