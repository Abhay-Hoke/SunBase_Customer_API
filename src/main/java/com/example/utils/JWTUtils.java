package com.example.utils;

import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JWTUtils {

	private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256); // Generate key 
	
	
	
	public static String generateToken(String username, String role) { 
		return Jwts.builder()
				.setSubject(username).claim("role", role)
				.setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + 3600000))
				.signWith(SECRET_KEY) .compact(); 
		}
	 
	   

	public static Claims validateToken(String token) {
		return Jwts.parser()
				.setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody();
		}

}
