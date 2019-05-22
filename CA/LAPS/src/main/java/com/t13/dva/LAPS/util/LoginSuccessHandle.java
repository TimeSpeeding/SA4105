package com.t13.dva.LAPS.util;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class LoginSuccessHandle implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
		String basepath = "http://localhost:8080";
		if (roles.contains("ROLE_ADMIN")) {
			response.sendRedirect(basepath + "/admin/hello");
			return;
		} else if (roles.contains("ROLE_STAFF")) {
			response.sendRedirect(basepath + "/staff/hello");
			return;
		} else if (roles.contains("ROLE_MANAGER")) {
			response.sendRedirect(basepath + "/manager/hello");
			return;
		}
		response.sendRedirect(basepath + "/login");
	}

}
