package com.tpe.security;

import com.tpe.security.service.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtils {

    private long expirationTime =86400000; //24*60*60*100
    private String secretKey = "techpro";

    //token: header + payload(userla ilgili bilgileri) + signature
    //Bearer esfsfFSAFSAASFA12asfsaf

    //1-kullanici giris yaptiginda JWT Token veriez generate edicez: içine username koyacağız
    public String generateToken(Authentication authentication){

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();//principal: authentice olan kullanıcının temel bilgileri

        //tokenin içine username bilgisini koyalım
        return Jwts.builder().//jwt oluşturucuyu getirir
                setSubject(userDetails.getUsername()).
                setIssuedAt(new Date()).//System.currentMillis()-->24.08 21.09
                setExpiration(new Date(new Date().getTime()+expirationTime)).//-->25.08 21.09
                signWith(SignatureAlgorithm.HS512,secretKey).
                //hash fonk ile tek yönlü şifreleme
                compact(); //ayarları tamamlar ve tokenı oluşturur
    }

    //2-kullanici tekrar req gönderdiğinde request ile birlikte Token vericek kontrol edicez (Tokeni Valide edicez)
    public boolean validateToken(String token){
        try{
            Jwts.parser()//ayrıştırıcı
                    .setSigningKey(secretKey)//anahtarı set ediyoruz
                    .parseClaimsJws(token);//anahtar uyumlu ise, JWT token geçerli
            return true;
        }catch (ExpiredJwtException e){
            e.printStackTrace();
        }catch (UnsupportedJwtException e){
            e.printStackTrace();
        }catch (SignatureException e){
            e.printStackTrace();
        }catch (MalformedJwtException e){
            e.printStackTrace();
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }

        return false;
    }



    //3-JWT Tokendan username i alacağız
    public String getUsernameFromToken(String token){
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)//doğrulanmış tokenın claimlerini getirir(header,payload,signature)
                .getBody()
                .getSubject();//bana username bilgisini getirmiş olucak
    }

}
