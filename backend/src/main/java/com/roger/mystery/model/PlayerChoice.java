package com.roger.mystery.model;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class PlayerChoice {
    @NotBlank
    private String choiceId;
    private Integer timeTaken;
    private LocalDateTime timestamp = LocalDateTime.now();
    public String getChoiceId() { return choiceId; }
    public void setChoiceId(String choiceId) { this.choiceId = choiceId; }
    public Integer getTimeTaken() { return timeTaken; }
    public void setTimeTaken(Integer timeTaken) { this.timeTaken = timeTaken; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
