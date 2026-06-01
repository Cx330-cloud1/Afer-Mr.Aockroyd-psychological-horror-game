package com.roger.mystery.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class Choice {
    private String id;
    private String text;
    private String nextNodeId;
    private Boolean quickTimeEvent = false;
    private Integer timeLimit;
    private Map<String, Condition> requirements = new LinkedHashMap<>();
    private Map<String, Integer> effects = new LinkedHashMap<>();
    private String flashbackTrigger;
    private String specialEffect;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public String getNextNodeId() { return nextNodeId; }
    public void setNextNodeId(String nextNodeId) { this.nextNodeId = nextNodeId; }
    public Boolean getQuickTimeEvent() { return quickTimeEvent; }
    public void setQuickTimeEvent(Boolean quickTimeEvent) { this.quickTimeEvent = quickTimeEvent; }
    public Integer getTimeLimit() { return timeLimit; }
    public void setTimeLimit(Integer timeLimit) { this.timeLimit = timeLimit; }
    public Map<String, Condition> getRequirements() { return requirements; }
    public void setRequirements(Map<String, Condition> requirements) { this.requirements = requirements; }
    public Map<String, Integer> getEffects() { return effects; }
    public void setEffects(Map<String, Integer> effects) { this.effects = effects; }
    public String getFlashbackTrigger() { return flashbackTrigger; }
    public void setFlashbackTrigger(String flashbackTrigger) { this.flashbackTrigger = flashbackTrigger; }
    public String getSpecialEffect() { return specialEffect; }
    public void setSpecialEffect(String specialEffect) { this.specialEffect = specialEffect; }
}
