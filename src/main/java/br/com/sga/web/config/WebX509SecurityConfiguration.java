package br.com.sga.web.config;

public class WebX509SecurityConfiguration {
}
//@EnableWebSecurity
//public class WebX509SecurityConfiguration extends WebSecurityConfigurerAdapter {
//	
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.authorizeRequests().antMatchers("/*").permitAll().and().csrf().disable();;
//	}
//	
//	@Override
//	public void configure(WebSecurity web) throws Exception {
//		web.ignoring()
//			.antMatchers("/resources/**", "/auth/**", "/errors/**");
//	}
//}