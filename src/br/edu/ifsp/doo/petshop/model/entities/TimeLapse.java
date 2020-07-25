package br.edu.ifsp.doo.petshop.model.entities;

import java.time.DateTimeException;
import java.time.LocalDateTime;

public class TimeLapse {
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public TimeLapse(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        if (startTime.isBefore(LocalDateTime.now()))
            throw new IllegalArgumentException("Data inicial inválida!");

        this.startTime = startTime;
    }

    public void setStartTime(int year, int month, int dayOfMonth, int hour, int minute) {
        LocalDateTime newTime;

        try {
            newTime = LocalDateTime.of(year, month, dayOfMonth, hour, minute);
        } catch (DateTimeException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Data inicial inválida!");
        }

        if (newTime.isBefore(LocalDateTime.now()))
            throw new IllegalArgumentException("Data inicial inválida!");

        this.startTime = newTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        if (endTime.isBefore(this.startTime))
            throw new IllegalArgumentException("Data final inválida!");

        this.endTime = endTime;
    }

    public void setEndTime(int year, int month, int dayOfMonth, int hour, int minute) {
        LocalDateTime newTime;

        try {
            newTime = LocalDateTime.of(year, month, dayOfMonth, hour, minute);
        } catch (DateTimeException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Data final inválida!");
        }

        if (newTime.isBefore(this.startTime))
            throw new IllegalArgumentException("Data final inválida!");

        this.startTime = newTime;
    }
}
