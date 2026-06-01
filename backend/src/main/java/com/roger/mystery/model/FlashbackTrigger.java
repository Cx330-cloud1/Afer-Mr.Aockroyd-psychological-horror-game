package com.roger.mystery.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class FlashbackTrigger {
    private String id;
    private TriggerType type;
    private String targetNodeId;
    private String targetChoiceId;
    private String targetInteractableId;
    private Map<String, Condition> conditions = new LinkedHashMap<>();
    private String flashbackId;
    private Integer intensity = 5;

    public enum TriggerType { CHOICE_SELECTED, VARIABLE_THRESHOLD, ITEM_INTERACTED, RANDOM_EVENT, PROGRESS_MILESTONE }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public TriggerType getType() { return type; }
    public void setType(TriggerType type) { this.type = type; }
    public String getTargetNodeId() { return targetNodeId; }
    public void setTargetNodeId(String targetNodeId) { this.targetNodeId = targetNodeId; }
    public String getTargetChoiceId() { return targetChoiceId; }
    public void setTargetChoiceId(String targetChoiceId) { this.targetChoiceId = targetChoiceId; }
    public String getTargetInteractableId() { return targetInteractableId; }
    public void setTargetInteractableId(String targetInteractableId) { this.targetInteractableId = targetInteractableId; }
    public Map<String, Condition> getConditions() { return conditions; }
    public void setConditions(Map<String, Condition> conditions) { this.conditions = conditions; }
    public String getFlashbackId() { return flashbackId; }
    public void setFlashbackId(String flashbackId) { this.flashbackId = flashbackId; }
    public Integer getIntensity() { return intensity; }
    public void setIntensity(Integer intensity) { this.intensity = intensity; }
}
