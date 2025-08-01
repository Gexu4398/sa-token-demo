package com.gregory.satokendemo.ssomodel.datasource;

import com.gregory.satokendemo.ssomodel.repository.BaseRepositoryImpl;
import com.zaxxer.hikari.HikariDataSource;
import java.util.Map;
import javax.sql.DataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.util.StringUtils;

@Configuration
@EnableJpaRepositories(
    repositoryBaseClass = BaseRepositoryImpl.class,
    basePackages = "com.gregory.satokendemo.ssomodel.repository",
    entityManagerFactoryRef = "ssoEntityManager",
    transactionManagerRef = "ssoTransactionManager")
public class Sso {

  private final Environment environment;

  @Autowired
  public Sso(Environment environment) {

    this.environment = environment;
  }

  private static SpringLiquibase springLiquibase(DataSource dataSource,
      LiquibaseProperties properties) {

    final var liquibase = new SpringLiquibase();
    liquibase.setDataSource(dataSource);
    liquibase.setChangeLog(properties.getChangeLog());
    liquibase.setContexts(StringUtils.collectionToCommaDelimitedString(properties.getContexts()));
    liquibase.setDefaultSchema(properties.getDefaultSchema());
    liquibase.setDropFirst(properties.isDropFirst());
    liquibase.setShouldRun(properties.isEnabled());
    liquibase.setLabelFilter(
        StringUtils.collectionToCommaDelimitedString(properties.getLabelFilter()));
    liquibase.setChangeLogParameters(properties.getParameters());
    liquibase.setRollbackFile(properties.getRollbackFile());
    return liquibase;
  }

  @Bean
  @ConfigurationProperties(prefix = "app.datasource.sso")
  public DataSourceProperties ssoDataSourceProperties() {

    return new DataSourceProperties();
  }

  @Bean
  @Primary
  @ConfigurationProperties(prefix = "app.datasource.sso.hikari")
  public HikariDataSource ssoDataSource() {

    return ssoDataSourceProperties()
        .initializeDataSourceBuilder()
        .type(HikariDataSource.class)
        .build();
  }

  @Bean(name = "ssoEntityManager")
  @Primary
  @Profile("test")
  public LocalContainerEntityManagerFactoryBean ssoEntityManagerForTest() {

    final var showSql = environment.getProperty("app.show-sql", "false");
    LocalContainerEntityManagerFactoryBean em
        = new LocalContainerEntityManagerFactoryBean();
    em.setDataSource(ssoDataSource());
    em.setPackagesToScan("com.gregory.satokendemo.ssomodel.model");
    em.setJpaVendorAdapter(ssoHibernateJpaVendorAdapter());
    em.setJpaPropertyMap(Map.of(
        // 因为 H2 和 PostgresSQL 的大文本类型不一致，因此不能开启 validate，否则会出异常
        "hibernate.hbm2ddl.auto", "none",
        "hibernate.show_sql", showSql
    ));
    return em;
  }

  @Bean
  @Primary
  @Profile("!test")
  public LocalContainerEntityManagerFactoryBean ssoEntityManager() {

    final var showSql = environment.getProperty("app.show-sql", "false");
    LocalContainerEntityManagerFactoryBean em
        = new LocalContainerEntityManagerFactoryBean();
    em.setDataSource(ssoDataSource());
    em.setPackagesToScan("com.gregory.satokendemo.ssomodel.model");
    em.setJpaVendorAdapter(ssoHibernateJpaVendorAdapter());
    em.setJpaPropertyMap(Map.of(
        "hibernate.hbm2ddl.auto", "validate",
        "hibernate.show_sql", showSql
    ));
    return em;
  }

  @Bean
  @Primary
  public HibernateJpaVendorAdapter ssoHibernateJpaVendorAdapter() {

    final var hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
    hibernateJpaVendorAdapter.setShowSql(true);
    hibernateJpaVendorAdapter.setGenerateDdl(true);
    return hibernateJpaVendorAdapter;
  }

  @Bean
  @Primary
  public JpaTransactionManager ssoTransactionManager() {

    JpaTransactionManager transactionManager
        = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(
        ssoEntityManager().getObject());
    return transactionManager;
  }

  @Bean
  @Primary
  @ConfigurationProperties(prefix = "app.datasource.sso.liquibase")
  public LiquibaseProperties ssoLiquibaseProperties() {

    return new LiquibaseProperties();
  }

  @Bean
  @Primary
  @ConditionalOnProperty(name = "app.datasource.sso.liquibase.enabled", havingValue = "true")
  public SpringLiquibase ssoLiquibase() {

    return springLiquibase(ssoDataSource(), ssoLiquibaseProperties());
  }
}
