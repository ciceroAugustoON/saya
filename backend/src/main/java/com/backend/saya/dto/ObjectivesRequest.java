package com.backend.saya.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ObjectivesRequest {
    private Integer dailySpendedHours;
    private Integer metaReduction;
    private List<Long> habitsHad;
    private List<Long> desiredHabits;
}
