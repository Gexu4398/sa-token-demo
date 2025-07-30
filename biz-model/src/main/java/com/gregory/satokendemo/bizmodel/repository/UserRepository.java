package com.gregory.satokendemo.bizmodel.repository;

import com.gregory.satokendemo.bizmodel.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseRepository<User, String> {

}
