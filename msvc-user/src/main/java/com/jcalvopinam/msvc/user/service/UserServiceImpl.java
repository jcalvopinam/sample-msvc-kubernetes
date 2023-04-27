package com.jcalvopinam.msvc.user.service;

import com.jcalvopinam.msvc.user.domain.User;
import com.jcalvopinam.msvc.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getUsers() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return findById(id);
    }

    private User findById(final Long id) {
        return userRepository.findById(id)
                             .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    @Transactional
    public User save(final User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User update(final Long id, final User user) {
        final User currentUser = findById(id);
        currentUser.setName(user.getName());
        currentUser.setEmail(user.getEmail());
        currentUser.setPassword(user.getPassword());
        return userRepository.save(currentUser);
    }

    @Override
    @Transactional
    public void delete(final Long id) {
        final User currentUser = findById(id);
        userRepository.delete(currentUser);
    }

}
