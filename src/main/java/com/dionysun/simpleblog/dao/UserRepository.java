package com.dionysun.simpleblog.dao;

import com.dionysun.simpleblog.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findUserByNickName(String nickname);
}
