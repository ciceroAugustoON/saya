package com.backend.saya.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponse {
    private Long id;
    private String name;
    private String description;
    private Integer difficulty;
    private Integer timeSecs;
    private String habit;
}
