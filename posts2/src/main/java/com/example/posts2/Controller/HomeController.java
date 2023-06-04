package com.example.posts2.Controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@RestController
public class HomeController {

    @GetMapping("/ping")
    public String getPing() {
        return "running";
    }

    @GetMapping("/posts")
    public ResponseEntity<String> getPosts(HttpServletRequest request) {

        if(!request.getHeader("Authorization").equals("teja")) {
            System.out.println("Received request from unknown source");
            return null;
        }

        String URL = "https://jsonplaceholder.typicode.com/posts";
        HttpHeaders headers = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();
        headers.set("content-type","application/json; charset=utf-8");
        HttpEntity<String> entity = new HttpEntity(headers);

        ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.GET, entity, String.class);
        return ResponseEntity.ok(response.getBody());
    }
}
