package com.devrezaur.main.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.devrezaur.main.model.Role;
import com.devrezaur.main.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class MyUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String fullname;
	private String username;
	private Collection<? extends GrantedAuthority> authorities;

	@JsonIgnore
	private String password;

	public MyUserDetails(Long id, String fullname, String username, String password, Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.fullname = fullname;
		this.username = username;
		this.password = password;
		this.authorities = authorities;
	}

	public static MyUserDetails build(User user) {
		List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
		
		for (Role role : user.getRoles()) {
			roles.add(new SimpleGrantedAuthority(role.getRole()));
		}

		return new MyUserDetails(user.getId(), user.getFullname(), user.getUsername(), user.getPassword(), roles);
	}

	public Long getId() {
		return id;
	}

	public String getFullname() {
		return fullname;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		MyUserDetails user = (MyUserDetails) o;
		return Objects.equals(id, user.id);
	}

}
