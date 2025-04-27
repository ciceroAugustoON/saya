package com.backend.saya.services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import com.backend.saya.entities.TokenAccess;
import com.backend.saya.repositories.TokenAccessRepository;

import jakarta.xml.bind.DatatypeConverter;

@Component
public class LoginSecurity {
	@Autowired
	private TokenAccessRepository tokenAccessRepository;

	public String encode(String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(password.getBytes());
			byte[] digest = md.digest();
			return DatatypeConverter.printHexBinary(digest).toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "";
		}
	}

	private TokenAccess generateToken(Long userId) {
		TokenAccess tokenAccess = new TokenAccess(userId, encode(new Date().toString()));
		return tokenAccess;
	}

	public TokenAccess getTokenAccess(Long userId) {
		Example<TokenAccess> tokenAccessExample = Example.of(new TokenAccess(userId, null));
		if (tokenAccessRepository.exists(tokenAccessExample)) {
			TokenAccess tokenAccess = tokenAccessRepository.findOne(tokenAccessExample).get();
			if (tokenAccess.isValid()) return tokenAccess;
		}
		TokenAccess tokenAccess = generateToken(userId);
		tokenAccess = tokenAccessRepository.saveAndFlush(tokenAccess);
		return tokenAccess;
	}
	
	public boolean validateToken(TokenAccess tokenAccess) {
		return (tokenAccess.isValid())? true : false;
	}

	public boolean matches(String password, String passwordEncrypted) {
		return Objects.equals(encode(password), passwordEncrypted);
	}
}
