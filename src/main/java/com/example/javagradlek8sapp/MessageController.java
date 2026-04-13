package com.example.javagradlek8sapp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/messages")
public class MessageController {
    private final JdbcTemplate jdbcTemplate;

    public MessageController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping
    public List<Message> getAllMessages() {
        return jdbcTemplate.query(
            "SELECT id, content, created_at FROM messages ORDER BY id DESC",
            (rs, rowNum) -> mapMessage(rs)
        );
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Message createMessage(@RequestBody CreateMessageRequest request) {
        if (request == null || request.content() == null || request.content().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "content is required");
        }

        return jdbcTemplate.queryForObject(
            "INSERT INTO messages (content) VALUES (?) RETURNING id, content, created_at",
            (rs, rowNum) -> mapMessage(rs),
            request.content().trim()
        );
    }

    private Message mapMessage(ResultSet rs) throws SQLException {
        Timestamp createdAt = rs.getTimestamp("created_at");
        Instant createdAtValue = createdAt != null ? createdAt.toInstant() : null;
        return new Message(rs.getLong("id"), rs.getString("content"), createdAtValue);
    }
}

record CreateMessageRequest(String content) {
}

record Message(long id, String content, Instant createdAt) {
}
