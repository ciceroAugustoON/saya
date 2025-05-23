package com.backend.saya.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HabitsRequest {
    private List<Long> habitsHadIds;
    private List<Long> desiredHabitsIds;
}
