package com.devrezaur.main.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.devrezaur.main.model.Role;
import com.devrezaur.main.model.User;

@Service
public class MyUserDetailsService implements UserDetailsService {
	
	@Autowired
	UserService userService;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userService.findUserByEmail(email);

		if(user == null) {
			throw new UsernameNotFoundException("User Not Found");
		}
		
		Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
		for (Role role : user.getRoles()) {
			roles.add(new SimpleGrantedAuthority(role.getRole()));
		}

		List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);

		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), grantedAuthorities);
	}

}
