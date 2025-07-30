package com.gregory.satokendemo.bizmodel.repository;

import com.gregory.satokendemo.bizmodel.model.UserEntity;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface SysUserRepository extends BaseRepository<UserEntity, String> {

  Optional<UserEntity> findByUsernameAndPassword(String username, String password);
}
