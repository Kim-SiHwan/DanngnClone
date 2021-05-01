package kim.sihwan.daangnclone.config.jwt;

import io.jsonwebtoken.*;
import kim.sihwan.daangnclone.service.MemberService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final MemberService memberService;
    public JwtTokenProvider (@Lazy MemberService memberService){
        this.memberService = memberService;
    }

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.token-validity-in-seconds}")
    long tokenValidityInSeconds;


    public String createToken(Authentication authentication){

        long now = (new Date()).getTime();
        Date validity = new Date(now + this.tokenValidityInSeconds);

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim("auth", authentication.getAuthorities())
                .signWith(SignatureAlgorithm.HS512, secret)
                .setExpiration(validity)
                .setIssuedAt(new Date(now))
                .compact();


    }

    public Authentication getAuthentication(String token){
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
        UserDetails userDetails = memberService.loadUserByUsername(claims.getSubject());
        return new UsernamePasswordAuthenticationToken(userDetails,"", userDetails.getAuthorities());
    }


    public boolean validateToken(String token, HttpServletRequest request) {
        try {
            Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            request.setAttribute("exception","InvalidTokenException");
        } catch (ExpiredJwtException e) {
            request.setAttribute("exception","ExpiredTokenException");
        } catch (UnsupportedJwtException e) {
            request.setAttribute("exception","UnsupportedTokenException");
        } catch (IllegalArgumentException e) {
            request.setAttribute("exception","IllegalArgumentTokenException");
        }
        return false;
    }

}
