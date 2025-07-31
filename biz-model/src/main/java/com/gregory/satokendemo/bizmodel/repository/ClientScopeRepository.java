package com.gregory.satokendemo.bizmodel.repository;

import com.gregory.satokendemo.bizmodel.model.ClientScope;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ClientScopeRepository extends BaseRepository<ClientScope, String> {

  @Query("""
      select distinct cs.name from ClientScope cs
      join cs.roles r
      join r.users u
      where u.id = :userId
      """)
  List<String> findDistinctNameByRoles_Users_Id(String userId);
}
