package util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by Administrator on 2018/4/11.
 *
 * 总结在SpringBoot在类读取application.yml配置信息
 *
 * application.yml
 *    jwt
 *       config
 *          key: itheima
 *
 *
 *  1）在属性上注入配置
 *       public class JwtUtil{
 *            @Value("jwt.config.key")
 *            private String key;
 *
 *        }
 *
 *   2）在类上面统一注入
 *        @ConfigurationProperties("jwt.config")
 *        public class JwtUtil{
 *
 *            private String key;
 *
 *        }
 *
 *   3)使用方法获取配置
 *
 *       publi void test(){
 *             String key = Environment.getProperty("jwt.config.key");
 *
 *       }
 *
 */
@ConfigurationProperties("jwt.config")
public class JwtUtil {

    private String key ;

    private long ttl ;//一个小时

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getTtl() {
        return ttl;
    }

    public void setTtl(long ttl) {
        this.ttl = ttl;
    }

    /**
     * 生成JWT
     *
     * @param id
     * @param subject
     * @return
     */
    public String createJWT(String id, String subject, String roles) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        JwtBuilder builder = Jwts.builder().setId(id)
                .setSubject(subject)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, key).claim("roles", roles);
        if (ttl > 0) {
            builder.setExpiration( new Date( nowMillis + ttl));
        }
        return builder.compact();
    }

    /**
     * 解析JWT
     * @param jwtStr
     * @return
     */
    public Claims parseJWT(String jwtStr){
        return  Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwtStr)
                .getBody();
    }

}
