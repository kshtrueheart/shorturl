package com.github.ququzone.shorturl;

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
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String create() {
        return "hello, world.";
    }
}
