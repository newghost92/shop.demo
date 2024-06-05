package com.shop.demo.config.dbConfig;

import com.zaxxer.hikari.HikariDataSource;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaAuditing(auditorAwareRef = "springSecurityAuditorAware")
@EnableJpaRepositories(
        entityManagerFactoryRef = "demoEntityManagerFactory",
        transactionManagerRef = "demoTransactionManager",
        basePackages = {
                "com.shop.demo.repository"
        }
)
public class MySQLConfig {

    @Value("${spring.datasource.flyway.locations}")
    private String locations;
    @Value("${spring.datasource.jdbc-url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String userName;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${datasource.hikari.maxLifeTime.master:600000}")
    private Integer maxLifeTime;
    @Value("${datasource.hikari.maximumPoolSize:30}")
    private Integer maximumPoolSize;
    @Value("${datasource.hikari.connectionTestQuery:SELECT 1}")
    private String connectionTestQuery;

    @PostConstruct
    public void migrateDatabase() {
        Flyway.configure().dataSource(url, userName, password).locations(locations).load().migrate();
    }

    @Primary
    @Bean(name = "demoDataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public HikariDataSource demoDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setAutoCommit(false);
        dataSource.setMaxLifetime(maxLifeTime);
        dataSource.setMaximumPoolSize(maximumPoolSize);
        dataSource.setConnectionTestQuery(connectionTestQuery);
        return dataSource;
    }

    @Primary
    @Bean(name = "demoEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean
    entityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("demoDataSource") DataSource dataSource
    ) {
        return builder
                .dataSource(dataSource)
                .packages("com.shop.demo.domain")
                .build();
    }

    @Primary
    @Bean(name = "demoTransactionManager")
    public PlatformTransactionManager demoTransactionManager(@Qualifier("demoEntityManagerFactory") EntityManagerFactory entityManagerFactory ) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
