package com.gregory.satokendemo.bizmodel.repository;

import com.gregory.satokendemo.bizmodel.model.User;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User, String> {

  Optional<User> findByUsernameAndPassword(String username, String password);
}
