/*
 * MIT License
 *
 * Copyright (c) 2023 JUAN CALVOPINA M
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package com.jcalvopinam.msvc.user.service;

import com.jcalvopinam.msvc.user.domain.User;
import com.jcalvopinam.msvc.user.exception.AlreadyExistsException;
import com.jcalvopinam.msvc.user.exception.BadRequestException;
import com.jcalvopinam.msvc.user.exception.NotFoundException;
import com.jcalvopinam.msvc.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jcalvopinam <juan.calvopina@gmail.com>
 */

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

    @Override
    @Transactional(readOnly = true)
    public List<User> getUsersByIds(final List<Long> ids) {
        return (List<User>) userRepository.findAllById(ids);
    }

    @Override
    @Transactional
    public User save(final User user, final BindingResult result) {
        validateRequest(result);

        if (existEmail(user.getEmail())) {
            throw new AlreadyExistsException("User already exists");
        }
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User update(final User user, final BindingResult result, final Long id) {
        validateRequest(result);

        final User currentUser = findById(id);

        if (!user.getEmail()
                 .equalsIgnoreCase(currentUser.getEmail()) && existEmail(user.getEmail())) {
            throw new AlreadyExistsException("User already exists");
        }

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

    @Override
    public boolean existEmail(final String email) {
        return userRepository.existsByEmail(email);
    }

    private User findById(final Long id) {
        return userRepository.findById(id)
                             .orElseThrow(() -> new NotFoundException("User not found"));
    }

    private void validateRequest(final BindingResult result) {
        final Map<String, String> errors = new HashMap<>();
        final List<FieldError> fieldErrors = result.getFieldErrors();

        if (!fieldErrors.isEmpty()) {
            fieldErrors.forEach(error -> {
                final String message = "The field " + error.getField() + " " + error.getDefaultMessage();
                errors.put(error.getField(), message);
            });
            throw new BadRequestException("Please check the following issues", errors);
        }
    }

}
