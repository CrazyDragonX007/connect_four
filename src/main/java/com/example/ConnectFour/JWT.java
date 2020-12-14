package com.example.ConnectFour;

import java.security.Key;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;  
import io.jsonwebtoken.SignatureAlgorithm;  

public class JWT {
	
	public static String jwt(int id, String user) {
		
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(getSignatureKey());
	    Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());  
	    String jwt = Jwts.builder().setIssuer(user)  
	    			.setSubject(Integer.toString(id))
	    			.signWith(SignatureAlgorithm.HS256,signingKey)  
	    			.compact();
	return jwt;
	}
	private static String getSignatureKey() {
		
		return "justanotherattemptatmakingaverylongsecretkey";
	}
	
	public static Claims decodeJWT(String jwt) {

        //This line will throw an exception if it is not a signed JWS (as expected)
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(getSignatureKey()))
                .parseClaimsJws(jwt).getBody();
        return claims;
	}

}
