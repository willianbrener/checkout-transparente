package br.com.sga.core.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
		DomainConfigurationParameters.PACKAGE_SERVICE, 
		DomainConfigurationParameters.PACKAGE_SUPPORT,
		DomainConfigurationParameters.PACKAGE_VALIDATOR
	})
public class DomainCoreConfiguration {
}