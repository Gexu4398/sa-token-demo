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
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Schema(description = "用户")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Schema(description = "用户ID")
  private String id;

  @Column(nullable = false, unique = true)
  @Schema(description = "用户名")
  private String username;

  @Column(nullable = false)
  @Schema(description = "密码")
  private String password;

  @Column
  @Schema(description = "姓")
  private String firstName;

  @Column
  @Schema(description = "名")
  private String lastName;

  @Column(unique = true)
  @Schema(description = "邮箱")
  private String email;

  @Column
  @Schema(description = "手机号码")
  private String phoneNumber;

  @Schema(description = "是否启用")
  private boolean enabled = true;

  @Column(nullable = false)
  @Schema(description = "状态")
  private String status;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "user_role",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id"))
  @Exclude
  private Set<Role> roles = new HashSet<>();

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "user_group",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "group_id"))
  @Exclude
  private Set<Group> groups = new HashSet<>();

  @Column
  @CreationTimestamp
  private LocalDateTime createdAt;

  @Column
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
    User v = (User) o;
    return id != null && Objects.equals(id, v.id);
  }

  @Override
  public int hashCode() {

    return getClass().hashCode();
  }
}
