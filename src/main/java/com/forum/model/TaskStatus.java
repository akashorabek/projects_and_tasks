package com.forum.model;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public enum TaskStatus {
    TO_DO("ToDo"),
    IN_PROGRESS("InProgress"),
    DONE("Done");

    @Getter
    private String label;
    private static final Map<String, TaskStatus> BY_LABEL = new HashMap<>();

    static {
        for(TaskStatus status: values()) {
            BY_LABEL.put(status.label, status);
        }
    }

    TaskStatus(String label) {
        this.label = label;
    }

    // Method for getting status by its label
    public static TaskStatus valueOfLabel(String label) {
        return BY_LABEL.get(label);
    }
}
