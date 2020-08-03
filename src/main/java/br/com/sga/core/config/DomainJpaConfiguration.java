package br.com.sga.core.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.persistence.SharedCacheMode;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.WebLogicJtaTransactionManager;

@Configuration
//@EnableTransactionManagement
//@EnableJpaRepositories(basePackages = DomainConfigurationParameters.PACKAGE_REPOSITORY_JPA)
//@EnableJpaRepositories(basePackages = DomainConfigurationParameters.PACKAGE_REPOSITORY_JPA, enableDefaultTransactions = false, entityManagerFactoryRef="entityManagerFactory")
public class DomainJpaConfiguration {
	
//    @Bean(destroyMethod = "")
//    public DataSource dataSource() {
//        JndiDataSourceLookup jndiDataSourceLookup = new JndiDataSourceLookup();
//        jndiDataSourceLookup.setResourceRef(true);
//        return jndiDataSourceLookup.getDataSource("jdbc/itcdweb");
//    }
//
//    @Bean
//    public JpaVendorAdapter jpaVendorAdapter() {
//        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//        vendorAdapter.setDatabase(Database.ORACLE);
//        vendorAdapter.setShowSql(true);
//        vendorAdapter.setGenerateDdl(false);
//        return vendorAdapter;
//    }
//
//    @Bean
//    public EntityManagerFactory entityManagerFactory(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {
//        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
//        factoryBean.setJtaDataSource(dataSource);
//        factoryBean.setJpaVendorAdapter(jpaVendorAdapter);
////        factoryBean.setJpaProperties(buildJpaProperties());
//        factoryBean.setPackagesToScan("br.com.sga.core.model");
//        factoryBean.setSharedCacheMode(SharedCacheMode.ENABLE_SELECTIVE);
//        factoryBean.afterPropertiesSet();
//        return factoryBean.getObject();
//    }
//
////    private Properties buildJpaProperties() {
////        Properties properties = new Properties();
////        properties.setProperty("hibernate.bytecode.use_reflection_optimizer", "true");
////        properties.setProperty("hibernate.cache.provider_class", "net.sf.ehcache.hibernate.SingletonEhCacheProvider");
////        properties.setProperty("hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.SingletonEhCacheRegionFactory");
////        properties.setProperty("hibernate.cache.provider_configuration_file_resource_path", "/ehcache.xml");
////        properties.setProperty("hibernate.cache.use_query_cache", "true");
////        properties.setProperty("hibernate.cache.use_second_level_cache", "true");
////        properties.setProperty("hibernate.show_sql", "true");
////        properties.setProperty("hibernate.format_sql", "true");
////        properties.setProperty("hibernate.hbm2ddl.auto", "none");
////        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.Oracle12cDialect");
////        properties.setProperty("hibernate.transaction.jta.platform", "org.hibernate.service.jta.platform.internal.WeblogicJtaPlatform");
////        properties.setProperty("hibernate.transaction.manager_lookup_class", "org.hibernate.transaction.WeblogicTransactionManagerLookup");
////        properties.setProperty("hibernate.temp.use_jdbc_metadata_defaults", "false");
////        return properties;
////    }
//    
//    @Bean
//    public PlatformTransactionManager transactionManager() {
//        return new WebLogicJtaTransactionManager();
//    }
//
//    @Bean
//    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
//        return new PersistenceExceptionTranslationPostProcessor();
//    }
//
//    @Bean
//    public PersistenceAnnotationBeanPostProcessor annotationBean() {
//        return new PersistenceAnnotationBeanPostProcessor();
//    }
}