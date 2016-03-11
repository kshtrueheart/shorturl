package com.github.ququzone.shorturl;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicLong;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * application configuration.
 *
 * @author Yang XuePing
 */
@Configuration
@EnableTransactionManagement
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@MapperScan(basePackages = {"com.github.ququzone"})
public class ApplicationConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationConfiguration.class);

    @Autowired
    private Environment env;

    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(env.getProperty("jdbc.driver"));
        config.setJdbcUrl(env.getProperty("jdbc.url"));
        config.setUsername(env.getProperty("jdbc.username"));
        config.setPassword(env.getProperty("jdbc.password"));
        Properties datasourceProperties = new Properties();
        datasourceProperties.setProperty("cachePrepStmts", env.getProperty("hikari.cachePrepStmts"));
        datasourceProperties.setProperty("prepStmtCacheSize", env.getProperty("hikari.prepStmtCacheSize"));
        datasourceProperties.setProperty("prepStmtCacheSqlLimit", env.getProperty("hikari.prepStmtCacheSqlLimit"));
        config.setDataSourceProperties(datasourceProperties);
        return new HikariDataSource(config);
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        try {
            SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
            sessionFactory.setDataSource(dataSource());
            sessionFactory
                    .setConfigLocation(new DefaultResourceLoader()
                            .getResource("classpath:/mybatis-config.xml"));

            return sessionFactory.getObject();
        } catch (Exception e) {
            logger.warn("Could not configure mybatis session factory");
            return null;
        }
    }

    @Bean
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public CuratorFramework curatorFramework() {
        CuratorFramework client = CuratorFrameworkFactory.newClient(env.getProperty("zookeeper.connectionString"), new ExponentialBackoffRetry(1000, 3));
        client.start();
        return client;
    }

    @Bean
    public DistributedAtomicLong distributedAtomicLong() {
        return new DistributedAtomicLong(curatorFramework(), env.getProperty("zookeeper.counter.node"),
                new ExponentialBackoffRetry(1000, 3));
    }
}
