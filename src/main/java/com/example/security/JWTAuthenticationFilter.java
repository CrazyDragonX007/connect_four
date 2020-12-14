package com.example.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.ConnectFour.Utility;
import com.example.entity.Games;
import com.example.rest.repo.GameRepo;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	@Autowired
	private GameRepo gameRepo;
	
	private AuthenticationManager authenticationManager;
	
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
			throws AuthenticationException {
		try {
			String result = IOUtils.toString(req.getInputStream());
			//String user=req.getInputStream().readAllBytes().toString();
			int x=result.indexOf("=");
			int y=result.indexOf('&');
			String username=result.substring(x+1, y);
			System.out.println(username);
			//User creds = new ObjectMapper().readValue(req.getInputStream(), User.class);
			int a=result.indexOf("password=");
			String password=result.substring(a+9);
			System.out.println(password);
			return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,
					password, new ArrayList<>()));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
			Authentication auth) throws IOException {
		
		//new Utility(gameRepo).newGame();
		Games game = new Games();
		game.setWinner(0);
		game.setExpired(false);
		//System.out.println(game.toString());
		gameRepo.save(game);
		//int gameid = game.getId();
		//System.out.println(gameid);
		//String token = (new GameController()).newGame();
		//Object user=(auth.getPrincipal());
		//String username = ((UserDetails)user).getUsername();
		//System.out.println(applicationContext);
		//GameRepo gameRepo=(GameRepo)applicationContext.getBean(GameRepo.class);
		
		//System.out.println(username);
		//String jwt = JWT.jwt(gameid,username);

		//String body = (username+ " " + jwt);

		//res.getWriter().write(body);
		//res.getWriter().flush();
	}
}
