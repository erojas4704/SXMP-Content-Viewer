package com.eddiejrojas.sxmproject.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContentDTO {
    private Long id;
    private String title;
    private String description;
    private String name;
    private String source;
    private String audioUrl;
    private String imageUrl;
}
