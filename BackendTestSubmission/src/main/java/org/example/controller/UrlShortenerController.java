package org.example.controller;

import org.example.model.UrlRequest;
import org.example.service.UrlShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
public class UrlShortenerController {

    @Autowired
    private UrlShortenerService service;


    @PostMapping("/shorten")
    public ResponseEntity<String> shortenUrl(@RequestBody UrlRequest request) {
        service.saveUrl(request);
        String shortUrl = "http://localhost:8080/" + request.getShortCode();
        return ResponseEntity.ok(shortUrl);
    }


    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirectToLongUrl(@PathVariable String shortCode) {
        String longUrl = service.getLongUrl(shortCode);
        if (longUrl != null) {
            return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(longUrl)).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
