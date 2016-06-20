package com.github.ququzone.shorturl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.regex.Pattern;

/**
 * short url controller.
 *
 * @author Yang XuePing
 */
@Controller
public class ShorturlController {
    private static Pattern URL = Pattern.compile("https?://[^\\s]+");

    @Autowired
    private UrlService service;

    @Autowired
    private Environment env;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<String> create(@RequestParam("url") String url) {
        if (!URL.matcher(url).matches()) {
            ResponseEntity.badRequest().body("invalid url");
        }
        return ResponseEntity.ok(GsonUtils.DEFAULT_GSON.toJson(service.generate(url)));
    }

    @RequestMapping(value = "/{code}", method = RequestMethod.GET)
    public String redirect(@PathVariable("code") String code) {
        Url url = service.getUrlByCode(code);
        if (url == null)
            return "redirect:http://" + env.getProperty("base.url");
        return "redirect:" + url.getUrl();
    }
}
