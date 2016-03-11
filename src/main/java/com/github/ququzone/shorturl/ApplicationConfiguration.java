package com.github.ququzone.shorturl;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicLong;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * application configuration.
 *
 * @author Yang XuePing
 */
@Configuration
@EnableAutoConfiguration
public class ApplicationConfiguration {
    @Autowired
    private Environment env;

    @Bean(destroyMethod = "close")
    public JedisPool jedisPool() {
        return new JedisPool(new JedisPoolConfig(), env.getProperty("redis.host"));
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
