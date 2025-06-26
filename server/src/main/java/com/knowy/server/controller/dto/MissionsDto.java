package com.knowy.server.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MissionsDto {
    private String name;
    private int currentProgress;
    private int totalProgress;
    private String value;
    private String label;


	public float getFractionProgress() {
        return (float) getCurrentProgress() / getTotalProgress() * 100;
    }
}
