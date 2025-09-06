package ru.ruslan.spring.SensorsService.dto;

public class RainyDaysCountResponse {
    private Long rainyDaysCount;

    public RainyDaysCountResponse(Long rainyDaysCount) {
        this.rainyDaysCount = rainyDaysCount;
    }

    public Long getRainyDaysCount() {
        return rainyDaysCount;
    }

    public void setRainyDaysCount(Long rainyDaysCount) {
        this.rainyDaysCount = rainyDaysCount;
    }
}
