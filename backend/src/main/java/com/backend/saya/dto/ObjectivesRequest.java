package com.backend.saya.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ObjectivesRequest {
    private int dailySpendedHours;
    private int metaReduction;
    private Long[] habitsHad;
    private Long[] desiredHabits;
}
