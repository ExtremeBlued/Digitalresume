package com.mvc.cryptovault.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.crypto.spec.SecretKeySpec;
import javax.security.auth.login.LoginException;
import javax.xml.bind.DatatypeConverter;
import java.math.BigInteger;
import java.security.Key;
import java.util.Date;

@Component
public class JwtHelper {

    public static String serviceName;
    public static Long expire;
    public static Long refresh;
    public static String base64Secret;

    public static Claims parseJWT(String jsonWebToken) {

        try {
            return Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(base64Secret))
                    .parseClaimsJws(jsonWebToken).getBody();
        } catch (Exception ex) {
            return null;
        }
    }

    public static String createToken(String username, BigInteger userId) {
        return createJWT(username, userId, expire, "token");
    }

    public static String createRefresh(String username, BigInteger userId) {
        return createJWT(username, userId, refresh, "refresh");
    }

    private static String createJWT(String username, BigInteger userId, Long expire, String type) {
        SignatureAlgorithm signatureAlgorithm = S