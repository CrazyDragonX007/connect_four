package com.example.security;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.example.ConnectFour.JWT;
import com.example.ConnectFour.StaticApplicationContext;
import com.example.entity.User;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	public JWTAuthorizationFilter(AuthenticationManager authManager) {
		super(authManager);
	}

	private EntityManagerFactory entityManagerFactory = (EntityManagerFactory) StaticApplicationContext.getContext()
			.getBean("entityManagerFactory");
	SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		String header = req.getHeader("Authorization");
		UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(req, res);
	}

	// Reads the JWT from the Authorization header, and then uses JWT to validate
	// the token
	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		System.out.println(token);
		if (token != null) {
			String userName = JWT.decodeJWT(token).getIssuer();
			System.out.println(userName);
			Session session = sessionFactory.openSession();
			String queryString = "from User";
			Query query = session.createQuery(queryString);
			List result = query.getResultList();
			User usr = new User();
			boolean check = false;
			for (int i = 0; i < result.size(); i++) {
				usr = (User) result.get(i);
				String st = usr.getUsername();
				if (st.equals(userName)) {
					check = true;
					break;
				}
			}
			if (check) {
				final List<GrantedAuthority> ADMIN_ROLES = AuthorityUtils.createAuthorityList("ROLE_ADMIN");
				Collection<? extends GrantedAuthority> authorities = ADMIN_ROLES;
				return new UsernamePasswordAuthenticationToken(userName, usr.getPassword(), authorities);
			}
			return null;
		}
		return null;
	}
}