package Auth.Filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

public class JwtAuthorizationFilter extends OncePerRequestFilter{

	@Autowired
	private JwtUtils jwtUtils;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if(request.getServletPath().equals("/api/refreshToken")) {
			filterChain.doFilter(request, response);
		}else {
			
			String authorizationTocken = request.getHeader("Authorization");
			if(authorizationTocken != null && authorizationTocken.startsWith("Bearer ")) {
				try {
					String jwt = authorizationTocken.substring(7);
					Algorithm algo = Algorithm.HMAC256(jwtUtils.jwtSecret);
					JWTVerifier jwtverify =JWT.require(algo).build();
					DecodedJWT decodedJwt =jwtverify.verify(jwt);
					String username = decodedJwt.getSubject();
					String[] roles = decodedJwt.getClaim("roles").asArray(String.class); 
					Collection< GrantedAuthority> authorities =new ArrayList<>();
					for(String r:roles) {
						authorities.add(new SimpleGrantedAuthority(r));
					}
					
					UsernamePasswordAuthenticationToken authenticationTocken = new UsernamePasswordAuthenticationToken(username,null,authorities);
					SecurityContextHolder.getContext().setAuthentication(authenticationTocken);
					filterChain.doFilter(request, response);
				
				} catch (Exception e) {
					response.setHeader("error-message", e.getMessage());
					response.sendError(HttpServletResponse.SC_FORBIDDEN);
				}
					
			
				}else {
						filterChain.doFilter(request, response);
			}
		}
	
	}

}