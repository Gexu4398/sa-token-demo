package com.gregory.satokendemo.bizmodel.repository;

import com.gregory.satokendemo.bizmodel.model.UserEntity;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface UserEntityRepository extends BaseRepository<UserEntity, String> {

  boolean existsByUsername(String username);

  boolean existsByEmail(String email);

  Optional<UserEntity> findByUsername(String username);
}
