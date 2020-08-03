package br.com.sga.web.config;

import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.CharEncoding;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.format.FormatterRegistry;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import br.com.sga.core.formatter.converter.DataStringConverter;
import br.com.sga.core.formatter.factory.CepFormatAnnotationFormatterFactory;
import br.com.sga.core.formatter.factory.DateCustomFormatAnnotationFormatterFactory;
import br.com.sga.core.formatter.factory.DateLongFormatAnnotationFormatterFactory;
import br.com.sga.core.formatter.factory.DateRangeFormatAnnotationFormatterFactory;
import br.com.sga.core.formatter.factory.DoubleSuffixFormatAnnotationFormatterFactory;
import br.com.sga.core.formatter.factory.HourStringFormatAnnotationFormatterFactory;
import br.com.sga.core.formatter.factory.MoedaFormatAnnotationFormatterFactory;
import br.com.sga.web.thymeleaf.AppDialect;
import nz.net.ultraq.thymeleaf.LayoutDialect;

@Configuration
@EnableWebMvc
@EnableSpringDataWebSupport
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan(basePackages = { "br.com.sga.web.controller"})
public class WebMvcConfiguration extends WebMvcConfigurerAdapter implements ApplicationContextAware {

	private ApplicationContext applicationContext;

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
	
    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(thymeleafTemplateResolver());
        templateEngine.setEnableSpringELCompiler(true);
        templateEngine.addDialect(new SpringSecurityDialect());
        templateEngine.addDialect(new LayoutDialect());
        templateEngine.addDialect(new AppDialect());
        return templateEngine;
    }
 
    @Bean
    public SpringResourceTemplateResolver thymeleafTemplateResolver() {
        SpringResourceTemplateResolver templateResolver 
          = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("classpath:/views/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML5");
        templateResolver.setCharacterEncoding(CharEncoding.UTF_8);
        return templateResolver;
    }
    
    @Bean
    public ThymeleafViewResolver thymeleafViewResolver() {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine());
		viewResolver.setOrder(1);
        return viewResolver;
    }
	
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	registry.addResourceHandler("/resources/**")
			.addResourceLocations("/resources/") 				 
			.addResourceLocations("classpath:/webapp/resources/")
    		.addResourceLocations("classpath:/front/");
    }
    
    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource resource = new ReloadableResourceBundleMessageSource();
        resource.setBasename("classpath:/messages/messages");
        resource.setDefaultEncoding(CharEncoding.UTF_8);
        return resource;
    }
    
    @Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver();
		resolver.setOneIndexedParameters(true);
		resolver.setMaxPageSize(100);
		argumentResolvers.add(resolver);

//	    SortHandlerMethodArgumentResolver sortResolver = new SortHandlerMethodArgumentResolver();
//	    sortResolver.setFallbackSort(Sort.by(Sort.Direction.DESC, "id"));
//	    argumentResolvers.add(sortResolver);
		
		super.addArgumentResolvers(argumentResolvers);
	}
    

    
    @Override
    public void addFormatters (FormatterRegistry registry) {
    	
        DataStringConverter dataString = new DataStringConverter();
        registry.addConverter(dataString);

        DateCustomFormatAnnotationFormatterFactory date = new DateCustomFormatAnnotationFormatterFactory();
        DateLongFormatAnnotationFormatterFactory dateLong = new DateLongFormatAnnotationFormatterFactory();
        HourStringFormatAnnotationFormatterFactory hourInteger = new HourStringFormatAnnotationFormatterFactory();
        MoedaFormatAnnotationFormatterFactory moeda = new MoedaFormatAnnotationFormatterFactory();
        DoubleSuffixFormatAnnotationFormatterFactory doubleSuffix = new DoubleSuffixFormatAnnotationFormatterFactory();
        CepFormatAnnotationFormatterFactory cep = new CepFormatAnnotationFormatterFactory();
        DateRangeFormatAnnotationFormatterFactory dateRange = new DateRangeFormatAnnotationFormatterFactory();

        registry.addFormatterForFieldAnnotation(date);
        registry.addFormatterForFieldAnnotation(dateLong);
        registry.addFormatterForFieldAnnotation(hourInteger);
        registry.addFormatterForFieldAnnotation(moeda);
        registry.addFormatterForFieldAnnotation(doubleSuffix);
        registry.addFormatterForFieldAnnotation(cep);
        registry.addFormatterForFieldAnnotation(dateRange);
    }

	@Bean
	public LocaleResolver localeResolver() {
		return new FixedLocaleResolver(new Locale("pt", "BR"));
	}
}