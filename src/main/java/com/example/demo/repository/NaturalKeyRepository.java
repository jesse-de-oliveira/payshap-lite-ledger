package com.example.demo.repository;

import com.example.demo.entity.NaturalKey;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NaturalKeyRepository extends JpaRepository<NaturalKey, UUID> {

    // Derived Query Method: Spring parses this method signature to automatically generate
    // a SQL SELECT query matching the aliasValue column. Wrapped in Optional to safely
    // handle cases where the alias does not exist in the database.
    Optional<NaturalKey> findByAliasValue(String aliasValue);
}
