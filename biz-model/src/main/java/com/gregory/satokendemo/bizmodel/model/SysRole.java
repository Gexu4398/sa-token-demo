package com.gregory.satokendemo.bizmodel.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.ToString.Exclude;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Builder
@Table(name = "sys_role")
@Schema(description = "角色")
public class SysRole {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Schema(description = "角色ID")
  private String id;

  @Column(nullable = false, unique = true)
  @Schema(description = "角色名称")
  private String name;

  @Column(columnDefinition = "text")
  @Schema(description = "角色描述")
  private String description;

  @Column(nullable = false)
  @Schema(description = "是否启用", defaultValue = "true")
  @Default
  private boolean enabled = true;

  @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
  @Exclude
  @Default
  private Set<UserEntity> users = new HashSet<>();

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "scope_mapping",
      joinColumns = @JoinColumn(name = "role_id"),
      inverseJoinColumns = @JoinColumn(name = "scope_id")
  )
  @Exclude
  @Default
  private Set<ClientScope> scopes = new HashSet<>();

  @Column(name = "created_at")
  @CreationTimestamp
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  @UpdateTimestamp
  private LocalDateTime updatedAt;

  @Override
  public boolean equals(Object o) {

    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    SysRole v = (SysRole) o;
    return id != null && Objects.equals(id, v.id);
  }

  @Override
  public int hashCode() {

    return getClass().hashCode();
  }
}
