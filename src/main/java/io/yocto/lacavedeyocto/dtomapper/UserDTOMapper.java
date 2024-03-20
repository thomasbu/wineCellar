package io.yocto.lacavedeyocto.dtomapper;

import io.yocto.lacavedeyocto.domain.User;
import io.yocto.lacavedeyocto.dto.UserDTO;
import org.springframework.beans.BeanUtils;

public class UserDTOMapper {
    public static UserDTO fromUser (User user){
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        return userDTO;
    }

    public static User toUser (UserDTO userDTO){
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        return user;
    }
}
