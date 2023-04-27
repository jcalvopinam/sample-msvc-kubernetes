package com.jcalvopinam.msvc.user.controller;

import com.jcalvopinam.msvc.user.domain.User;
import com.jcalvopinam.msvc.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/")
public class UserController {

    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        return ResponseEntity.status(HttpStatus.OK)
                             .body(userService.getUsers());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> findById(@PathVariable final Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                             .body(userService.getUserById(id));
    }

    @PostMapping
    public ResponseEntity<User> save(@RequestBody final User user) {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(userService.save(user));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<User> update(@PathVariable final Long id, @RequestBody final User user) {
        return ResponseEntity.status(HttpStatus.OK)
                             .body(userService.update(id, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable final Long id) {
        userService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                             .build();
    }

}
