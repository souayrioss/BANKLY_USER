package org.roronoa.banklyuser.service;



import org.roronoa.banklyuser.dto.AuthDto;
import org.roronoa.banklyuser.entity.User;

import java.util.List;

public interface IUserService {
    User save(User user);
    User getUser(String uuid);
    List<User> getListUsers();

    User findByEmail(AuthDto authDto);
    User validateToken(String token);


}