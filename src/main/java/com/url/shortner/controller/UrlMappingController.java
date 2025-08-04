package com.url.shortner.controller;

import com.url.shortner.dtos.ClickEventDTO;
import com.url.shortner.dtos.UrlMappingDTO;
import com.url.shortner.models.User;
import com.url.shortner.service.UrlMappingService;
import com.url.shortner.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;


import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/urls")
@AllArgsConstructor
@Tag(name = "URL Shortener", description = "Endpoints for URL shortening and analytics")
public class UrlMappingController {

    private final UrlMappingService urlMappingService;
    private final UserService userService;

    @Operation(
            summary = "Create a shortened URL",
            description = "Takes a long URL and returns a shortened version for the authenticated user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Short URL created successfully")
            }
    )
    @PostMapping("/shorten")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UrlMappingDTO> createShortUrl(
            @RequestBody Map<String, String> request,
            Principal principal) {

        String originalUrl = request.get("originalUrl");
        User user = userService.findByUsername(principal.getName());
        UrlMappingDTO urlMappingDTO = urlMappingService.createShortUrl(originalUrl, user);
        return ResponseEntity.ok(urlMappingDTO);
    }

    @Operation(
            summary = "Get user's shortened URLs",
            description = "Fetches all shortened URLs created by the authenticated user"
    )
    @GetMapping("/myurls")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<UrlMappingDTO>> getUserUrls(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        List<UrlMappingDTO> urls = urlMappingService.getUrlsByUSer(user);
        return ResponseEntity.ok(urls);
    }

    @Operation(
            summary = "Get click analytics for a short URL",
            description = "Returns click event data between a start and end date for a specific short URL"
    )
    @GetMapping("/analytics/{shortUrl}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<ClickEventDTO>> getUserAnalytics(
            @Parameter(description = "Short URL to analyze") @PathVariable String shortUrl,
            @Parameter(description = "Start date in ISO_LOCAL_DATE_TIME format") @RequestParam("startDate") String startDate,
            @Parameter(description = "End date in ISO_LOCAL_DATE_TIME format") @RequestParam("endDate") String endDate) {

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime start = LocalDateTime.parse(startDate, formatter);
        LocalDateTime end = LocalDateTime.parse(endDate, formatter);
        List<ClickEventDTO> clickEventDTOS = urlMappingService.getClickEventsByDate(shortUrl, start, end);
        return ResponseEntity.ok(clickEventDTOS);
    }

    @Operation(
            summary = "Get total click counts by date",
            description = "Returns a map of click counts grouped by date for the authenticated user"
    )
    @GetMapping("/totalClicks")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Map<LocalDate, Long>> getTotalClicksByDate(
            Principal principal,
            @Parameter(description = "Start date in ISO_LOCAL_DATE format") @RequestParam("startDate") String startDate,
            @Parameter(description = "End date in ISO_LOCAL_DATE format") @RequestParam("endDate") String endDate) {

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        User user = userService.findByUsername(principal.getName());
        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);
        Map<LocalDate, Long> totalClicks = urlMappingService.getTotalClicksByUSerAndDate(user, start, end);
        return ResponseEntity.ok(totalClicks);
    }
}
