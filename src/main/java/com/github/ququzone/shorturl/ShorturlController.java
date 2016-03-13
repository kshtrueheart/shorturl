package com.github.ququzone.shorturl;

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
    @Autowired
    private UrlService service;

    @Autowired
    private Environment env;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public
    @ResponseBody
    String create(@RequestParam("url") String url) {
        return GsonUtils.DEFAULT_GSON.toJson(service.generate(url));
    }

    @RequestMapping(value = "/{code}", method = RequestMethod.GET)
    public String redirect(@PathVariable("code") String code) {
        Url url = service.getUrlByCode(code);
        if (url == null)
            return "redirect:http://" + env.getProperty("base.url");
        return "redirect:" + url.getUrl();
    }
}
