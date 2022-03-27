package com.forum.model.dto;

import com.forum.model.TaskRate;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class TaskRateDto {
    private String userEmail;
    private boolean liked;
    private boolean disliked;

    public static TaskRateDto from(TaskRate taskRate) {
        return TaskRateDto.builder()
                .userEmail(taskRate.getUser().getEmail())
                .liked(taskRate.isLiked())
                .disliked(taskRate.isDisliked())
                .build();
    }
}
