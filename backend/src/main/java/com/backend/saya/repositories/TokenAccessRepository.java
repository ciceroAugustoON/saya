package com.backend.saya.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.saya.entities.TokenAccess;

@Repository
public interface TokenAccessRepository  extends JpaRepository<TokenAccess, Long>{

	public TokenAccess findByToken(String token);
}
