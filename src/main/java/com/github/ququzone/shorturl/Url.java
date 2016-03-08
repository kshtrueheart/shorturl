package com.github.ququzone.shorturl;

import java.util.Date;
import java.util.UUID;

/**
 * url model.
 *
 * @author Yang XuePing
 */
public class Url {
    private String id;
    private String url;
    private String code;
    private Date createdTime;

    public void generateId() {
        if (id == null || "".equals(id))
            id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
}
