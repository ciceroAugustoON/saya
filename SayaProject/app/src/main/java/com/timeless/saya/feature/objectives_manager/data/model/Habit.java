package com.timeless.saya.feature.objectives_manager.data.model;

import com.timeless.saya.feature.objectives_manager.data.model.enumeration.Segmentation;

public class Habit {
    private Long id;
    private String name;
    private Segmentation segmentation;

    public Habit() {
    }

    public Habit(Long id, String name, Segmentation segmentation) {
        this.id = id;
        this.name = name;
        this.segmentation = segmentation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Segmentation getSegmentation() {
        return segmentation;
    }

    public void setSegmentation(Segmentation segmentation) {
        this.segmentation = segmentation;
    }
}
