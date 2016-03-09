package com.github.ququzone.shorturl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * short url controller.
 *
 * @author Yang XuePing
 */
@Controller
public class ShorturlController {
    private static final Gson DEFAULT_GSON = new GsonBuilder()
            .serializeNulls()
            .excludeFieldsWithoutExposeAnnotation()
            .setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    @Autowired
    private UrlService service;

    @Autowired
    private Environment env;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public
    @ResponseBody
    String create(@RequestParam("url") String url) {
        Url result = service.generate(url);
        result.setShortUrl(env.getProperty("base.url") + "/" + result.getCode());
        return DEFAULT_GSON.toJson(result);
    }

    @RequestMapping(value = "/{code}", method = RequestMethod.GET)
    public String redirect(@PathVariable("code") String code) {
        Url url = service.getUrlByCode(code);
        if (url == null)
            return "redirect:http://" + env.getProperty("base.url");
        return "redirect:" + url.getUrl();
    }
}
