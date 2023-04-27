package com.jcalvopinam.msvc.user.repository;

import com.jcalvopinam.msvc.user.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

}
