package br.com.sga.web.config.init;

public class ApplicationSecurityInitializer {}/*extends AbstractSecurityWebApplicationInitializer {

	@Override
	protected void beforeSpringSecurityFilterChain(ServletContext servletContext) {
		
		//avoid url JSESSION id URL rewritten, and save cookies
		servletContext.setSessionTrackingModes(EnumSet.of(SessionTrackingMode.COOKIE));
		
		Dynamic encodingFilter = servletContext.addFilter("encodingFilter", new CharacterEncodingFilter());
		encodingFilter.setInitParameter("encoding","UTF-8");
		encodingFilter.setInitParameter("forceEncoding", "true");
		encodingFilter.addMappingForUrlPatterns(null, false, "/*");
	}
}*/