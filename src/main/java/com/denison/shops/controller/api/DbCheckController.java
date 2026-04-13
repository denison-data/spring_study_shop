package com.denison.shops.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.sql.SQLException;
import javax.sql.DataSource;

@RestController
@RequestMapping("/api/db")
@RequiredArgsConstructor
@Slf4j
public class DbCheckController {

    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;

    @GetMapping("/health")
    public DbHealthResponse checkHealth() {
        DbHealthResponse response = new DbHealthResponse();

        try {
            // 1. 기본 연결 테스트
            String testQuery = jdbcTemplate.queryForObject("SELECT 'OK'", String.class);
            response.setStatus("UP");
            response.setMessage("Database connection successful");

            // 2. 상세 정보 수집
            try (var conn = dataSource.getConnection()) {
                var meta = conn.getMetaData();
                response.setDatabase(meta.getDatabaseProductName());
                response.setUrl(meta.getURL());
                response.setUser(meta.getUserName());
                response.setDriver(meta.getDriverName());
                response.setValid(conn.isValid(2));
            }

            // 3. 테이블 개수 확인
            String dbName = jdbcTemplate.queryForObject("SELECT DATABASE()", String.class);
            Integer tableCount = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = ?",
                    Integer.class, dbName);
            response.setTableCount(tableCount);

        } catch (Exception e) {
            response.setStatus("DOWN");
            response.setMessage("Database connection failed: " + e.getMessage());
            response.setValid(false);
        }

        return response;
    }

    @Data
    private static class DbHealthResponse {
        private String status;
        private String message;
        private String database;
        private String url;
        private String user;
        private String driver;
        private Boolean valid;
        private Integer tableCount;
    }
}