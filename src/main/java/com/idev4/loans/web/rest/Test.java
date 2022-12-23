package com.idev4.loans.web.rest;

import com.idev4.loans.web.rest.util.HeaderUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class Test {


    @GetMapping("/hello")
    public ResponseEntity<String> sayHello(@RequestParam("username") String username) {

        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("Test", "Hello Mr/Mrs " + username))
                .body("Hello World");
    }
}
