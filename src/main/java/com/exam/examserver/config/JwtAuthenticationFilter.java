package com.exam.examserver.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.exam.examserver.helper.UserFoundException;
import com.exam.examserver.helper.UserNotFoundException;
import com.exam.examserver.servicesImpl.UserDetailsServiceImpl;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
			
		final String requestTokenHeader = request.getHeader("Authorization");
		System.out.println(requestTokenHeader);
		String userName =null;
		String jwtToken = null;
		
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {

			jwtToken = requestTokenHeader.substring(7);
			try {
				userName = this.jwtUtils.getUsernameFromToken(jwtToken);
			} catch (ExpiredJwtException e) {
				e.printStackTrace();
				System.out.println("jwt token expired");
			} catch (IllegalArgumentException e) {
				// logger.info("Illegal Argument while fetching the username !!");
				e.printStackTrace();
				System.out.println("IllegalArgumentException exception..");
			} catch (MalformedJwtException e) {
				// logger.info("Some changed has done in token !! Invalid Token");
				e.printStackTrace();
				System.out.println(e.getMessage());
				//throw new UserFoundException();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Exception exception..");
			}
		} else {
			System.out.println("Invalid token, not start with bearer string");
		}
		
		
		//validate token
		if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			final UserDetails userDetails = this.userDetailsServiceImpl.loadUserByUsername(userName);
			if(this.jwtUtils.validateToken(jwtToken, userDetails)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthentication);
			}
		}else {
			System.out.println("token not valid");
		}
		
		filterChain.doFilter(request, response);
		
	}

}
