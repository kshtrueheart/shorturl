package com.github.ququzone.shorturl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * short url controller.
 *
 * @author Yang XuePing
 */
@RestController
public class ShorturlController {
    @Autowired
    private UrlService service;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Url create() {
        return service.generate("ddd");
    }
}
