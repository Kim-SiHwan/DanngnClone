package kim.sihwan.daangnclone.config.jwt;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        // 유효한 자격증명을 제공하지 않고 접근하려 할때 401
        String exception="";
        try{
            exception = request.getAttribute("exception").toString();

        }catch (NullPointerException e){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            System.out.println("널!");
            return;
        }

        if(exception.equals("ExpiredTokenException")){
            System.out.println("만료");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        if(exception.equals("InvalidTokenException")){
            System.out.println("잘못됨");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        if(exception.equals("UnsupportedTokenException")){
            System.out.println("언서폿");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        if(exception.equals("IllegalArgumentTokenException")){
            System.out.println("일리갈");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

    }
}