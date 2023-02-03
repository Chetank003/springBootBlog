package blog.security;

import blog.services.CustomUserDetails;
import blog.services.CustomUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthenticationFilter extends BasicAuthenticationFilter {

    private final String header;
    private final String prefix;
    private final String secret;
    private final CustomUserDetailsService userDetailsService;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, String header, String prefix, String secret, CustomUserDetailsService userDetailsService) {
        super(authenticationManager);
        this.header = header;
        this.prefix = prefix;
        this.secret = secret;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(this.header);
        this.logger.info("Header: "+ header);
        if(header == null || !header.startsWith(this.prefix)){
            SecurityContextHolder.clearContext();
            throw new IOException("Failed to process authentication request");
        }
        UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(request);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken(HttpServletRequest request) {
        String token = request.getHeader(this.header);
        if(token!= null){
            token = token.replace(this.prefix, "");
            try {
                String user = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody().getSubject();
                if(user != null){
                    CustomUserDetails userDetails = userDetailsService.loadUserByUsername(user);
                    return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                }
            } catch (ExpiredJwtException e){
                this.logger.error("Unauthorised request");
            }
        }
        return null;
    }
}
