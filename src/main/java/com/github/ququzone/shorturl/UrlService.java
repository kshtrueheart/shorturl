package com.github.ququzone.shorturl;

import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicLong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.regex.Pattern;

/**
 * url service.
 *
 * @author Yang XuePing
 */
@Service
@Transactional
public class UrlService {
    private static final Logger LOG = LoggerFactory.getLogger(UrlService.class);

    private static Pattern URL = Pattern.compile("https?://[^\\s]+");

    @Autowired
    private UrlMapper urlMapper;

    @Autowired
    private DistributedAtomicLong distributedAtomicLong;

    public Url generate(String url) {
        try {
            if (URL.matcher(url).matches()) {
                throw new RuntimeException("invalid url");
            }
            AtomicValue<Long> value = distributedAtomicLong.increment();
            if (value.succeeded()) {
                Url result = new Url();
                result.generateId();
                result.setUrl(url);
                result.setCreatedTime(new Date());
                result.setCode(CodeTransfer.decode(value.preValue()));
                urlMapper.insert(result);
                return result;
            }
            throw new RuntimeException("increment counter fail.");
        } catch (Exception e) {
            LOG.error("generate url exception", e);
            throw new RuntimeException("generate url exception");
        }
    }

    public Url getUrlByCode(String code) {
        return urlMapper.findByCode(code);
    }
}
