package br.com.sga.web.config.init;

/**
 * Classe responsável por iniciar as configurações da aplicação
 */
public class ApplicationInitializer /*extends AbstractAnnotationConfigDispatcherServletInitializer*/ {

	/*@Override
	protected Class<?>[] getRootConfigClasses() {
//		try {
			*//***
			 * Carrega classes de configuração comuns a todos os tipos de
			 * implementações
			 ***//*
			Class<?>[] rootConfigClasses = DomainInitializer.getRootConfigClasses();

			// Configurações de segurança da aplicação
//			rootConfigClasses = ArrayUtils.add(rootConfigClasses, WebX509SecurityConfiguration.class);

			return rootConfigClasses;
	}*/


	/**
	 * Retorna a classe que instrui o Dispatcher a localizar os Controllers.
	 * 
	 * @return
	 */
	/*@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] { WebMvcConfiguration.class};
	}*/

	/**
	 * Método responsável por definir o padrão de URL que será delegado para o
	 * DispatcherServlet. "/" significa que tudo após a / será entregue ao
	 * Dispatcher (qualquer URL dentro da aplicação).
	 *
	 * <servlet-mapping> <url-pattern>/</url-pattern> </servlet-mapping>
	 * 
	 * @return
	 */
	/*@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}*/

	/*@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		
		//configuração do cookie no path da aplicação para evitar erro de substituir o JSESSIONID
		servletContext.setSessionTrackingModes(EnumSet.of(SessionTrackingMode.COOKIE));
		
		SessionCookieConfig sessionCookieConfig = servletContext.getSessionCookieConfig();
		// Indicate this cookie won't be explosed to javascript (httpOnly = true) 
		sessionCookieConfig.setHttpOnly(true);
		// Indicate this cookie will live till browser closes (-1)
		sessionCookieConfig.setMaxAge(-1);
		// Indicate this cookie will be sent only to this path
		sessionCookieConfig.setPath(servletContext.getContextPath());

		// Open Entity Manager in View
//		OpenEntityManagerInViewSupport.addFilter(servletContext, WebMvcConfiguration.class);

		// Spring Request Context Listener
		servletContext.addListener(new RequestContextListener());

		super.onStartup(servletContext);
	}*/
}