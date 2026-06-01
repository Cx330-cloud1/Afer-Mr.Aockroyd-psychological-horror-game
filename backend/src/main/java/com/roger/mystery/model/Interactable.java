package com.roger.mystery.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class Interactable {
    private String id;
    private String name;
    private String description;
    private ActionType actionType = ActionType.EXAMINE;
    private String memoryId;
    private Boolean hidden = false;
    private Boolean disturbing = false;
    private String specialEffect;
    private String clueId;
    private Map<String, Condition> visibilityConditions = new LinkedHashMap<>();
    private Map<String, Integer> effects = new LinkedHashMap<>();

    public enum ActionType { EXAMINE, USE, COMBINE, HIDE, RECORD }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public ActionType getActionType() { return actionType; }
    public void setActionType(ActionType actionType) { this.actionType = actionType; }
    public String getMemoryId() { return memoryId; }
    public void setMemoryId(String memoryId) { this.memoryId = memoryId; }
    public Boolean getHidden() { return hidden; }
    public void setHidden(Boolean hidden) { this.hidden = hidden; }
    public Boolean getDisturbing() { return disturbing; }
    public void setDisturbing(Boolean disturbing) { this.disturbing = disturbing; }
    public String getSpecialEffect() { return specialEffect; }
    public void setSpecialEffect(String specialEffect) { this.specialEffect = specialEffect; }
    public String getClueId() { return clueId; }
    public void setClueId(String clueId) { this.clueId = clueId; }
    public Map<String, Condition> getVisibilityConditions() { return visibilityConditions; }
    public void setVisibilityConditions(Map<String, Condition> visibilityConditions) { this.visibilityConditions = visibilityConditions; }
    public Map<String, Integer> getEffects() { return effects; }
    public void setEffects(Map<String, Integer> effects) { this.effects = effects; }
}
