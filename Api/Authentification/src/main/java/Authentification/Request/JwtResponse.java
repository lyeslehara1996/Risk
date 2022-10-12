package Authentification.Request;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter  @Setter @Data @Component

public class JwtResponse {


	public JwtResponse(String jwtAccessTocken, String jwtRefreshToken, String username,String email, List<String> roles) {
		super();
		this.jwtAccessTocken = jwtAccessTocken;
		this.JwtRefreshToken = jwtRefreshToken;
		this.username = username;
		this.email=email;
		this.roles = roles;
	}
	private String jwtAccessTocken;
	private String JwtRefreshToken;
	private String username;
	private String email;
	private List<String> roles;



}