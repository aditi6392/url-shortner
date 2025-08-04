package com.url.shortner.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;


@Data
@Schema(description = "Data transfer object representing click count for a specific date")
public class ClickEventDTO {

    @Schema(description = "DAte on which URL was clicked")
    private LocalDate clickDate;
    @Schema(description = "Total number of times the URL was clicked on this date",example = "34")
    private Long count;

}
