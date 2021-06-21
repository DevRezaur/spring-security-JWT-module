package dev.rezaur.jwt.service;

import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import dev.rezaur.jwt.model.AppUser;
import dev.rezaur.jwt.model.Role;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UserService userService;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AppUser appUser = userService.findUserByUserName(username);

		if (appUser == null) {
			throw new UsernameNotFoundException("User Not Found");
		}

		Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
		for (Role role : appUser.getRoles()) {
			roles.add(new SimpleGrantedAuthority(role.getRole()));
		}

		List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);

		return new User(appUser.getUserName(), appUser.getPassword(), grantedAuthorities);
	}

}
