package com.gregory.satokendemo.bizmodel.repository;

import com.gregory.satokendemo.bizmodel.model.SysRole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SysRoleRepository extends BaseRepository<SysRole, String> {

  @Query("""
      select distinct r.name from SysRole r
      join r.users u
      where u.id = :userId
      """)
  List<String> findDistinctNameByUsers_Id(String userId);
}
