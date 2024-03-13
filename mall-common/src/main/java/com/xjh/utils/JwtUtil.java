package com.xjh.utils;

import io.jsonwebtoken.*;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;

public class JwtUtil {

    //过期时间为1小时
    public static final long JWT_TTL = 60 * 60 * 1000L;
    //jwt_key
    public static final String JWT_KEY = "xjh";

    //uuid表示token唯一
    public static String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    public static String createJwt(String subject){
        JwtBuilder jwtBuilder = getJwtBuilder(subject,null,getUUID());
        return jwtBuilder.compact();
    }

    public static String createJwt(String subject,Long ttlMills){
        JwtBuilder jwtBuilder = getJwtBuilder(subject,ttlMills,getUUID());
        return jwtBuilder.compact();
    }
    public static String createJwt(String id,String subject,Long ttlMills){
        JwtBuilder jwtBuilder = getJwtBuilder(subject,ttlMills,id);
        return jwtBuilder.compact();
    }

    //设置jwt的id，主体，签发人，签发时间，签名的算法和秘钥以及过期时间
    private static JwtBuilder getJwtBuilder(String subject,Long ttlMills,String uuid){
        SignatureAlgorithm algorithm = SignatureAlgorithm.HS256;
        SecretKey secretKey = generalKey();
        long nowMillis = System.currentTimeMillis();//当前时间
        Date now = new Date(nowMillis);
        if(ttlMills == null){
            ttlMills = JWT_TTL;
        }
        Date expDate = new Date(nowMillis + ttlMills);//过期时间为当前时间+持续时间
        //不用设置header，header中token类型默认是JWT，签名算法默认是HS256
        return Jwts.builder()
                .setId(uuid)
                .setSubject(subject)
                .setIssuer("xjh")
                .setIssuedAt(now)
                .setExpiration(expDate)
                .signWith(algorithm,secretKey);
    }

    public static void main(String[] args) {
        System.out.println(createJwt("name:zs"));
    }

    //将jwt_key进行Base64编码生成秘钥
    public static SecretKey generalKey(){
        byte[] encodeKey = Base64.getDecoder().decode(JWT_KEY);
        return new SecretKeySpec(encodeKey,0,encodeKey.length,"AES");
    }

    //用秘钥进行验签，验签成功获取body，也就是subject
    public static Claims parseJwt(String jwt){
        return Jwts.parser()
                .setSigningKey(generalKey())
                .parseClaimsJws(jwt)
                .getBody();
    }

}
