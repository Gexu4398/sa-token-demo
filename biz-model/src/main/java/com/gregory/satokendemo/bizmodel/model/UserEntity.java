package com.gregory.satokendemo.bizmodel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
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
@Table(
    name = "user_entity",
    indexes = {
        @Index(name = "idx_user_entity_username", columnList = "username")
    }
)
@Schema(description = "用户")
public class UserEntity {

  // 审核中
  public final static String STATUS_PENDING = "pending";

  // 审核拒绝
  public final static String STATUS_REJECTED = "rejected";

  // 正常
  public final static String STATUS_NORMAL = "normal";

  // 锁定
  public final static String STATUS_LOCKED = "locked";

  // 停用
  public final static String STATUS_DISABLED = "disabled";

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Schema(description = "用户ID")
  private String id;

  @Column(nullable = false, unique = true)
  @Schema(description = "用户名")
  private String username;

  @Column(nullable = false, columnDefinition = "text")
  @Schema(description = "密码")
  @JsonIgnore
  private String password;

  @Column(name = "first_name")
  @Schema(description = "姓")
  private String firstName;

  @Column(name = "last_name")
  @Schema(description = "名")
  private String lastName;

  @Column(unique = true)
  @Schema(description = "邮箱")
  private String email;

  @Column(name = "phone_number", length = 50)
  @Schema(description = "手机号码")
  private String phoneNumber;

  @Column
  @Schema(description = "用户头像")
  private String picture;

  @Column(nullable = false)
  @Schema(description = "是否启用", defaultValue = "true")
  @Default
  private boolean enabled = true;

  @Column(nullable = false, length = 50)
  @Schema(description = "状态")
  @Default
  private String status = STATUS_NORMAL;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "user_role_mapping",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id"))
  @Exclude
  @JsonIgnore
  @Default
  private Set<SysRole> roles = new HashSet<>();

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "user_group_membership",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "group_id"))
  @Exclude
  @JsonIgnore
  @Default
  private Set<SysGroup> groups = new HashSet<>();

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
    UserEntity v = (UserEntity) o;
    return id != null && Objects.equals(id, v.id);
  }

  @Override
  public int hashCode() {

    return getClass().hashCode();
  }

  public void addGroup(SysGroup sysGroup) {

    getGroups().add(sysGroup);
    sysGroup.getUsers().add(this);
  }

  public void addRole(SysRole sysRole) {

    getRoles().add(sysRole);
    sysRole.getUsers().add(this);
  }
}
