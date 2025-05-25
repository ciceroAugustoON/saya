package com.backend.saya.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelatoryResponse {
    private int offensive;
    private int timeSaved;
    private Map<Long, Integer> tasksFinishedByHabit;
}
