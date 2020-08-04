package br.com.sga.web.config;

import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationHandler /*implements AuthenticationFailureHandler*/ {

	/*@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {

		
        String redirect = "/auth/login/error?message=%s&username=%s";
        
		String message = exception.getMessage();
		
		String lastUserName = request.getParameter("username");
		
		response.sendRedirect(String.format(redirect, message, lastUserName));


	}*/
}