package teleDemo.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import teleDemo.entities.UserLogin;

import java.util.Date;
/**
 * @Project Name:trackmanage
 * @File Name: CreatJwt
 * @Description: 提供 JWT
 * @ HISTORY：
 *    Created   2022.8.22  WYJ
 *    Modified  2022.8.22  WYJ
 */
public class CreatJwt {
    private static final String SECRET_KEY = "wyj-322";

    public static String getoken(UserLogin user) {
        JwtBuilder jwtBuilder = Jwts.builder()
                //设置需要加密的内容
                .setId(user.getUserID() + "")
                .setSubject(user.getUserName())
                //token保留时间
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).setExpiration(
                        new Date(System.currentTimeMillis() + 86400000)
                );
        return jwtBuilder.compact();
    }
    public static UserLogin getUser(String token){

        Claims claims= Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        //解密ID
        Integer id=Integer.valueOf(claims.getId());
        //解密用户名
        String username=claims.getSubject();
        UserLogin user = new UserLogin(id,username,"",0);
        return user;
    }
}

