package com.ivoronline.springboot_db_datasource_create_entitiymanager_hibernate.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(
  basePackages            = "com.ivoronline.springboot_db_datasource_create_entitiymanager_hibernate.repository",
  entityManagerFactoryRef = "myEntityManagerFactoryBean"
)
public class MyDatabaseConfig {

  //PROPERTIES
  @Autowired private Environment env; //TO RECREATE TABLE
  
  //=========================================================================================================
  // DATA SOURCE PROPERTIES
  //=========================================================================================================
  @Bean
  @ConfigurationProperties("my.spring.datasource")
  public DataSourceProperties dataSourceProperties() {
    return new DataSourceProperties();
  }
  
  //=========================================================================================================
  // DATA SOURCE
  //=========================================================================================================
  @Bean
  public DataSource dataSource() {
    return dataSourceProperties().initializeDataSourceBuilder().build();
  }
  
  //=========================================================================================================
  // ENTITY MANAGER FACTORY
  //=========================================================================================================
  @Bean
  LocalContainerEntityManagerFactoryBean myEntityManagerFactoryBean() {
  
    //HIBERNATE PROPERTIES
    //Hibernate Properties from application.properties are ignored => We need to set them manually
    HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
                              hibernateJpaVendorAdapter.setGenerateDdl(true);
                              hibernateJpaVendorAdapter.setShowSql(true);
                              
    //ENTITY MANAGER
    LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
    emf.setDataSource      (dataSource());
    emf.setPackagesToScan  ("com.ivoronline.springboot_db_datasource_create_entitiymanager_hibernate.entity");
    emf.setJpaVendorAdapter(hibernateJpaVendorAdapter); //TO RECREATE TABLE
    emf.setJpaProperties   (additionalProperties());          //TO RECREATE TABLE
    
    return emf ;
    
  }
  
  //=========================================================================================================
  // ADDITIONAL PROPERTIES
  //=========================================================================================================
  // TO RECREATE TABLE =>  Reading Hibernate Properties from application.properties
  private Properties additionalProperties() {
		Properties properties = new Properties();
		properties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
		properties.setProperty("hibernate.show_sql"    , env.getProperty("spring.jpa.show-sql"          ));
		return properties;
	}
 
}
