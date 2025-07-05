package org.example.repository;

import org.example.model.urlMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlMappingRepository extends JpaRepository<urlMapping, Long> {
    Optional<urlMapping> findByShortCode(String shortCode);
}
