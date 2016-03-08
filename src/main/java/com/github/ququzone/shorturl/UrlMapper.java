package com.github.ququzone.shorturl;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

/**
 * url mybatis mapper.
 *
 * @author Yang XuePing
 */
@Component
public interface UrlMapper {
    @Insert("insert into urls(id, url, code, created_time) values (#{id}, #{url}, #{code}, #{createdTime})")
    void insert(Url url);

    @Select("select id, url, code, created_time from urls where code = #{code}")
    @ResultMap("UrlResult")
    Url findByCode(@Param("code") String code);
}
