package com.github.ququzone.shorturl;

import com.google.gson.annotations.Expose;

import java.util.Date;

/**
 * url model.
 *
 * @author Yang XuePing
 */
public class Url {
    @Expose
    private String url;
    @Expose
    private String code;
    @Expose
    private Date createdTime;
    @Expose
    private String shortUrl;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }
}
