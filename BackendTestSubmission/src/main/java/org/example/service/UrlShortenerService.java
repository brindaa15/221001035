package org.example.service;

import org.example.model.UrlRequest;
import org.example.model.urlMapping;
import org.example.repository.UrlMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UrlShortenerService {

    @Autowired
    private UrlMappingRepository repo;

    public void saveUrl(UrlRequest req) {

        if (repo.findByShortCode(req.getShortCode()).isPresent()) {
            throw new RuntimeException("Short code already used.");
        }

        urlMapping map = new urlMapping();
        map.setShortCode(req.getShortCode());
        map.setLongUrl(req.getLongUrl());
        map.setExpiryDate(LocalDate.parse(req.getValidityDate()));

        repo.save(map);
    }

    public String getLongUrl(String shortCode) {
        urlMapping map = repo.findByShortCode(shortCode)
                .orElseThrow(() -> new RuntimeException("Short code not found."));

        if (map.getExpiryDate().isBefore(LocalDate.now())) {
            throw new RuntimeException("This URL has expired.");
        }

        return map.getLongUrl();
    }
}
