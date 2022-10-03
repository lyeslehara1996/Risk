package com.GatewatService.Filter;

import java.net.http.HttpHeaders;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractNameValueGatewayFilterFactory;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.google.common.base.Predicate;

import io.jsonwebtoken.Claims;
import reactor.core.publisher.Mono;

@Component
@RefreshScope
public class AuthentigationFilter implements GatewayFilter{

	@Autowired
	private RouterValidator routerValidation;

	@Autowired
	private JwtUtils jwtUtils;
	
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		
		ServerHttpRequest request = exchange.getRequest();
		// TODO Auto-generated method stub
		if(routerValidation.isSecured.equals(request)) {
			if (this.isAuthMissing(request))
                return this.onError(exchange, "Authorization header is missing in request", HttpStatus.UNAUTHORIZED);

				final String token = this.getAuthHeader(request);


				if (jwtUtils.isInvalid(token))
				    return this.onError(exchange, "Authorization header is invalid", HttpStatus.UNAUTHORIZED);
		
					this.populateRequestWithHeaders(exchange, token);
        }
   
    return chain.filter(exchange);

	}
	
    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    private String getAuthHeader(ServerHttpRequest request) {
        return request.getHeaders().getOrEmpty("Authorization").get(0);
    }

    private boolean isAuthMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey("Authorization");
    }

    private void populateRequestWithHeaders(ServerWebExchange exchange, String token) {
        Claims claims = jwtUtils.getAllClaimsFromToken(token);
        exchange.getRequest().mutate()
                .header("id", String.valueOf(claims.get("id")))
                .header("roles", String.valueOf(claims.get("roles")))
                .build();
    }

}
