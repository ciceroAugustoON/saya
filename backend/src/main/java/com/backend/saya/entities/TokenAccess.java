package com.backend.saya.entities;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_token")
public class TokenAccess implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long userId;
	private String token;
	private Date expireTime;
	
	public TokenAccess() {
		
	}

	public TokenAccess(Long userId, String token) {
		this.userId = userId;
		this.token = token;
		if (token != null) {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, 1);
			expireTime = cal.getTime();
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}
	
	public boolean isValid() {
		return (new Date().getTime() <= expireTime.getTime());
	}
}
