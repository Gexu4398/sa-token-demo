package com.gregory.satokendemo.ssomodel.repository;

import com.gregory.satokendemo.ssomodel.model.UserEntity;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface UserEntityRepository extends BaseRepository<UserEntity, String> {

  Optional<UserEntity> findByEmail(String email);

  boolean existsByUsername(String username);

  boolean existsByEmail(String email);

  Optional<UserEntity> findByUsername(String username);
}
