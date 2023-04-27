package com.jcalvopinam.msvc.user.service;

import com.jcalvopinam.msvc.user.domain.User;

import java.util.List;

public interface UserService {

    List<User> getUsers();

    User getUserById(Long id);

    User save(User user);

    User update(Long id, User user);

    void delete(Long id);

}
