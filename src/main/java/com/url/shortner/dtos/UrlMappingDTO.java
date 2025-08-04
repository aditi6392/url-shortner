package com.url.shortner.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "DTO representing the shortened URL information")
public class UrlMappingDTO {

    @Schema(description = "Unique identifier for the URL mapping", example = "1")
    private long id;

    @Schema(description = "The original long URL", example = "https://www.example.com/very/long/url")
    private String originalUrl;

    @Schema(description = "The shortened URL", example = "http://short.ly/abc123")
    private String shortUrl;

    @Schema(description = "Total number of times the short URL has been clicked", example = "27")
    private long clickCount;

    @Schema(description = "Timestamp when the short URL was created", type = "string", format = "date-time", example = "2025-08-04T12:45:00")
    private LocalDateTime createdDate;

    @Schema(description = "Username of the user who created the short URL", example = "aditi123")
    private String username;
}
