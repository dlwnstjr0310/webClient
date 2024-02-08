package com.web.practice.domain.entity.employee;

import com.web.practice.domain.common.CommonConstant;
import com.web.practice.domain.common.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Employee implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@NotNull
	@Size(max = 20)
	String empName;

	@NotNull
	@Column(unique = true)
	String account;

	@NotNull
	@Size(max = 255)
	String password;

	@NotNull
	@Size(max = 50)
	@Column(unique = true)
	@Pattern(regexp = CommonConstant.RegExp.EMAIL)
	String email;

	@Pattern(regexp = CommonConstant.RegExp.PHONE)
	String phone;

	String refreshToken;

	@Builder.Default
	@ColumnDefault("true")
	Boolean isLocked = true;

	@NotNull
	@Enumerated(EnumType.STRING)
	Role role;

	@NotNull
	@Enumerated(EnumType.STRING)
	Status.Department department;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(this.getRole().name()));
	}

	@Override
	public @NotNull String getUsername() {
		return account;
	}

	@Override
	public boolean isAccountNonExpired() {
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return isLocked;
	}

	public void setUpAdditionalInfo(Role role, Status.Department dept) {
		this.role = role;
		this.department = dept;
		this.isLocked = false;
	}
}
