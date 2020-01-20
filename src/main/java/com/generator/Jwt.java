package com.generator;

import java.io.File;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.Certificate;
import java.util.Base64;
import java.util.Date;

import org.springframework.core.io.Resource;
import org.springframework.util.ResourceUtils;
import org.springframework.core.io.ClassPathResource;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class Jwt {
	private PrivateKey privateKey;
	private PublicKey publicKey;

	public Jwt() {
		generateRsaKeys();
	}

	private void generateRsaKeys() {
		KeyPairGenerator keyGenerator;
		try {
			keyGenerator = KeyPairGenerator.getInstance("RSA");
			keyGenerator.initialize(1024);
			KeyPair kp = keyGenerator.generateKeyPair();
			privateKey = (PrivateKey) kp.getPrivate();
			publicKey = (PublicKey) kp.getPublic();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	/*
	 * JWT Token Body components: sub -> user name id -> user id iat -> when token
	 * was generated
	 */

	public String generateJwtToken(String name, String userId) {
		Date date = new Date();
		String token = Jwts.builder().setSubject(name).setIssuer("auth-api").setIssuedAt(date).claim("id", userId)
				.signWith(SignatureAlgorithm.RS256, privateKey).compact();
		return token;
	}

	public void printJwt(String token) {
		Jws parseClaimsJws = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token);
		System.out.println("Header     : " + parseClaimsJws.getHeader());
		System.out.println("Body       : " + parseClaimsJws.getBody());
		System.out.println("Signature  : " + parseClaimsJws.getSignature());
	}

	

}
