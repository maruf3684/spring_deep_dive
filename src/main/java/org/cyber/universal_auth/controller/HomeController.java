package org.cyber.universal_auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/xxx")
    public String hallowWorld(){
        return "hello world";
    }

}
