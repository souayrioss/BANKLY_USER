package org.roronoa.banklyuser.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.roronoa.banklyuser.dto.ResponseDTO;
import org.roronoa.banklyuser.entity.User;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Component
public class JwtUtils {
    private String jwtSingningKey = "secret";
    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token){
        return extractClaim(token,Claims::getExpiration);
    }


    public <T> T extractClaim(String token , Function<Claims,T> claimsResolver){
        if (extractAllClaims(token).getStatus().equals("bad request")){
            return null;
        }else {
            final Claims claims = (Claims) extractAllClaims(token).getData();

            return claimsResolver.apply(claims);
        }
    }


    public ResponseDTO extractAllClaims(String token){
        try {
            Claims claims = Jwts.parser().setSigningKey(jwtSingningKey).parseClaimsJws(token).getBody();
            return new ResponseDTO("success", "claims", claims);
        }catch (Exception e){
            return new ResponseDTO("bad request","jwt expired",e.getMessage());
        }

    }

    public Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(User userApp) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims,userApp);
    }

    public String createToken(Map<String,Object> claims, User userDetails){

        return Jwts.builder().setClaims(claims)
                .setSubject(userDetails.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ TimeUnit.HOURS.toMillis(24)))
                .signWith(SignatureAlgorithm.HS256,jwtSingningKey).compact();
    }
}
