package com.example.demo.repository;


import com.example.demo.entity.LedgerEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.UUID;

@Repository
public interface LedgerEntryRepository extends JpaRepository<LedgerEntry, UUID> {

    @Query("SELECT COALESCE(SUM(l.amount), 0) FROM LedgerEntry l WHERE l.account.id = :accountId AND l.entryType = :type")
    BigDecimal sumAmountByAccountIdAndType(@Param("accountId") UUID accountId, @Param("type") String type);
}
