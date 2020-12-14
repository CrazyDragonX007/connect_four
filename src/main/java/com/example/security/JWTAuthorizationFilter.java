package com.example.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.example.ConnectFour.JWT;
import com.example.ConnectFour.StaticApplicationContext;
import com.example.entity.User;
import com.example.rest.repo.UserRepo;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	public JWTAuthorizationFilter(AuthenticationManager authManager) {
		super(authManager);
	}
	// private UserRepo
	// userRepo=(UserRepo)StaticApplicationContext.getContext().getBean("userRepo");

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		String header = req.getHeader("Authorization");
		System.out.println(header);
//		if (header == null || !header.startsWith("BEARER")) {
//			System.out.println("11");
//			chain.doFilter(req, res);
//			return;
//		}
		UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(req, res);
	}

	// Reads the JWT from the Authorization header, and then uses JWT to validate
	// the token
	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		String token = request.getHeader("Authorization");

		if (token != null) {
			// parse the token.
			String username = JWT.decodeJWT(token).getIssuer();
			System.out.println(username);
			User user = new User();
			// user.getPassword();
			// user.getRole();
			if (user != null) {
				// new arraylist means authorities
				final List<GrantedAuthority> ADMIN_ROLES = AuthorityUtils
						.createAuthorityList("ROLE_ADMIN", "ROLE_USER");
				Collection<? extends GrantedAuthority> authorities = ADMIN_ROLES;
				return new UsernamePasswordAuthenticationToken(user, null, authorities);
			}

			return null;
		}

		return null;
	}
}