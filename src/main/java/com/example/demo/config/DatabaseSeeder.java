package com.example.demo.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.UUID;

@Configuration
public class DatabaseSeeder {

    @Bean
    public CommandLineRunner initDatabase(JdbcTemplate jdbcTemplate) {
        return args -> {
            // We use JdbcTemplate to write raw SQL directly to the database on startup
            Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM accounts", Integer.class);

            // Only seed if the database is completely empty
            if (count != null && count == 0) {
                System.out.println("Empty database detected. Seeding initial accounts...");

                List<String> users = List.of("Jesse", "Joshua", "Jethroe", "Jordan", "Caleigh", "Lara", "Gustavo");

                for (String name : users) {
                    UUID accountId = UUID.randomUUID();
                    String email = name.toLowerCase() + "@example.com";

                    // 1. Insert the Account
                    jdbcTemplate.update("INSERT INTO accounts (id, owner_name, balance, is_active, created_at) VALUES (?, ?, ?, ?, now())",
                            accountId, name, 0.00, true);

                    // 2. Insert the Natural Key (Email)
                    jdbcTemplate.update("INSERT INTO natural_keys (id, account_id, key_type, alias_value, is_active) VALUES (gen_random_uuid(), ?, 'EMAIL', ?, true)",
                            accountId, email);

                    // 3. Inject 10,000 into the ledger so transfers will pass the LedgerService checks
                    jdbcTemplate.update("INSERT INTO ledger_entries (id, account_id, amount, entry_type, created_at) VALUES (gen_random_uuid(), ?, 10000.00, 'CREDIT', now())",
                            accountId);

                    System.out.println(name + " UUID: " + accountId);
                }

                System.out.println("✅ Seeding complete. All accounts have been created with 10,000.00 each.");
            } else {
                System.out.println("Database already contains data. Skipping seed process.");
            }
        };
    }
}