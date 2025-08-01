package com.gregory.satokendemo.ssomodel.repository;

import com.gregory.satokendemo.ssomodel.model.SysRole;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SysRoleRepository extends BaseRepository<SysRole, String> {

  SysRole findByName(String name);

  @Query("""
      select distinct r.name from SysRole r
      join r.users u
      where u.id = :userId
      """)
  List<String> findDistinctNameByUsers_Id(String userId);
}
