package cgi.demo.services;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;



import java.util.Date;


@Component
public class JwtService {

    Algorithm algorithm = Algorithm.HMAC256(SECRET);

    public static final String SECRET = "357638792F423F4428472B4B6250655368566D597133743677397A2443264629";

    final int cookieExpiry = 3600 * 60 * 60;

    private final JWTVerifier verifier = JWT.require(algorithm).build();

    public String generateToken(String username, long expiry) {
        try {
            String token = JWT.create()
                    .withIssuer("auth0")
                    .withSubject(username)
                    .withExpiresAt(new Date(System.currentTimeMillis() + expiry))
                    .sign(algorithm);
            return token;
        } catch (Exception e) {
            return null;
        }


    }

    public String extractUsername(String token) {

        try {
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getSubject();
        }catch (Exception e){
            return null;
        }
    }


    public boolean validateToken(String token) {

        try{
           verifier.verify(token);
           return true;
        }catch (Exception e){
            return false;
        }
    }

    public String setTokenExpiryToLogout(String token){
        String subject = verifier.verify(token).getSubject();
        return generateToken(subject,0);

    }
}