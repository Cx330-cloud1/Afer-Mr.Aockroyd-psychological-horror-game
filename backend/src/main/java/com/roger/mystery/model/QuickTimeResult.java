package com.roger.mystery.model;

import java.time.LocalDateTime;

public class QuickTimeResult {
    private String choiceId;
    private Integer timeTaken;
    private LocalDateTime timestamp = LocalDateTime.now();

    public static QuickTimeResult of(String choiceId, Integer timeTaken) {
        QuickTimeResult r = new QuickTimeResult();
        r.setChoiceId(choiceId);
        r.setTimeTaken(timeTaken);
        return r;
    }
    public String getChoiceId() { return choiceId; }
    public void setChoiceId(String choiceId) { this.choiceId = choiceId; }
    public Integer getTimeTaken() { return timeTaken; }
    public void setTimeTaken(Integer timeTaken) { this.timeTaken = timeTaken; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
