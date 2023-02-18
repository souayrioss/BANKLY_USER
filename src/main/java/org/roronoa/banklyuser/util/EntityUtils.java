package org.roronoa.banklyuser.util;


import org.modelmapper.ModelMapper;
import org.roronoa.banklyuser.dto.UserDto;
import org.roronoa.banklyuser.entity.User;


import java.security.SecureRandom;



public class EntityUtils {

    private static final SecureRandom random = new SecureRandom();




    public static UserDto userToUserDTO(User user) {
        ModelMapper modelMapper=new ModelMapper();
        return modelMapper.map(user, UserDto.class);
    }
    public static User userDTOToUser(UserDto userDto) {
        ModelMapper modelMapper=new ModelMapper();
        return modelMapper.map(userDto, User.class);
    }


}
