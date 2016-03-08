package com.github.ququzone.shorturl;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicLong;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * url service.
 *
 * @author Yang XuePing
 */
@Service
@Transactional
public class UrlService {
    @Autowired
    private UrlMapper urlMapper;

    @Autowired
    private Environment env;

    @Autowired
    private DistributedAtomicLong distributedAtomicLong;

    public Url generate(String url) {
        try {
            AtomicValue<Long> value = distributedAtomicLong.increment();
            if (value.succeeded()) {
                Url result = new Url();
                result.generateId();
                result.setCreatedTime(new Date());
                result.setCode("" + value.preValue());
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
