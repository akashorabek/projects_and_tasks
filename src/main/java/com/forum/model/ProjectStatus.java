package com.forum.model;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum ProjectStatus {
    NOT_STARTED("NotStarted"),
    ACTIVE("Active"),
    COMPLETED("Completed");


    private String label;

    private static final Map<String, ProjectStatus> BY_LABEL = new HashMap<>();
    static {
        for(ProjectStatus status: values()) {
            BY_LABEL.put(status.label, status);
        }
    }

    ProjectStatus(String label) {
        this.label = label;
    }

    // Method for getting status by its label
    public static ProjectStatus valueOfLabel(String label) {
        return BY_LABEL.get(label);
    }
}
