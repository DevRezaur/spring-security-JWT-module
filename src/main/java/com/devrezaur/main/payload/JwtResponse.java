package com.devrezaur.main.payload;

import java.util.List;

public class JwtResponse {

	private String type;
	private String token;
	private Long userid;
	private String fullname;
	private String username;
	private List<String> roles;

	public JwtResponse(String type, String token, Long userid, String fullname, String username, List<String> roles) {
		super();
		this.type = type;
		this.token = token;
		this.userid = userid;
		this.fullname = fullname;
		this.username = username;
		this.roles = roles;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

}
