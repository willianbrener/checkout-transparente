package br.com.sga.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

//    @Autowired
//    private AccessDeniedHandler accessDeniedHandler;
	
	@Autowired
	private CustomAuthenticationProvider customAuthenticationProvider;
	
	@Autowired
	private CustomAuthenticationHandler customAuthenticationHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .formLogin()
                .loginPage("/login")
                .failureHandler(customAuthenticationHandler)
                .permitAll()
			.and()
				.authorizeRequests()        
				.antMatchers("/logout").permitAll()
				.anyRequest()
					.authenticated()
			.and()
				.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/auth/logout/success")
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID")
//            .and()
//                .exceptionHandling().accessDeniedHandler(accessDeniedHandler)
			.and()
				.sessionManagement()
					.sessionAuthenticationErrorUrl("/auth/logout/concurrency")
					.invalidSessionUrl("/auth/denied")
					.maximumSessions(1)
					.expiredUrl("/auth/logout/concurrency")
        ;
    }

    @Override
    public void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.authenticationProvider(customAuthenticationProvider);
    }

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
    
    //Spring Boot configured this already.
    @Override
    public void configure(WebSecurity web) throws Exception {
		web.ignoring()
		.antMatchers("/resources/**", "/auth/**", "/errors/**");
    }

}