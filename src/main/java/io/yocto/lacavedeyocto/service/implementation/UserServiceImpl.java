package io.yocto.lacavedeyocto.service.implementation;

import io.yocto.lacavedeyocto.domain.User;
import io.yocto.lacavedeyocto.dto.UserDTO;
import io.yocto.lacavedeyocto.dtomapper.UserDTOMapper;
import io.yocto.lacavedeyocto.repository.UserRepository;
import io.yocto.lacavedeyocto.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository<User> userRepository;
    @Override
    public UserDTO createUser(User user) {
        return UserDTOMapper.fromUser(userRepository.create(user));
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        return UserDTOMapper.fromUser(userRepository.getUserByEmail(email));
    }
}
