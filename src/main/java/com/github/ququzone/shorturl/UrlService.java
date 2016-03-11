package com.github.ququzone.shorturl;

import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicLong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Date;
import java.util.regex.Pattern;

/**
 * url service.
 *
 * @author Yang XuePing
 */
@Service
public class UrlService {
    private static final Logger LOG = LoggerFactory.getLogger(UrlService.class);

    private static Pattern URL = Pattern.compile("https?://[^\\s]+");

    @Autowired
    private JedisPool pool;

    @Autowired
    private DistributedAtomicLong distributedAtomicLong;

    @Autowired
    private Environment env;

    public Url generate(String url) {
        try (Jedis jedis = pool.getResource()) {
            if (!URL.matcher(url).matches()) {
                throw new RuntimeException("invalid url");
            }
            AtomicValue<Long> value = distributedAtomicLong.increment();
            if (value.succeeded()) {
                Url result = new Url();
                result.setUrl(url);
                result.setCreatedTime(new Date());
                result.setCode(CodeTransfer.decode(value.preValue()));
                result.setShortUrl(env.getProperty("base.url") + "/" + result.getCode());
                jedis.hset(env.getProperty("redis.urls.key"), result.getCode(), GsonUtils.DEFAULT_GSON.toJson(result));
                return result;
            }
            throw new RuntimeException("increment counter fail.");
        } catch (Exception e) {
            LOG.error("generate url exception", e);
            throw new RuntimeException(e.getMessage());
        }
    }

    public Url getUrlByCode(String code) {
        try (Jedis jedis = pool.getResource()) {
            String result = jedis.hget(env.getProperty("redis.urls.key"), code);
            return result == null ? null : GsonUtils.DEFAULT_GSON.fromJson(result, Url.class);
        }
    }
}
