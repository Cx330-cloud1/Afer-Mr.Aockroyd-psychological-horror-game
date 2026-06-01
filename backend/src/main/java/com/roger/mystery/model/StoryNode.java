package com.roger.mystery.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class StoryNode {
    private String id;
    private String scene;
    private String type;
    private String publicNarrative;
    private String innerThoughts;
    private String memoryTrigger;
    private Boolean disturbing = false;
    private List<Choice> choices = new ArrayList<>();
    private List<Interactable> interactables = new ArrayList<>();
    private Map<String, Integer> variableEffects = new LinkedHashMap<>();
    private Map<String, String> ambientEffects = new LinkedHashMap<>();

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getScene() { return scene; }
    public void setScene(String scene) { this.scene = scene; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getPublicNarrative() { return publicNarrative; }
    public void setPublicNarrative(String publicNarrative) { this.publicNarrative = publicNarrative; }
    public String getInnerThoughts() { return innerThoughts; }
    public void setInnerThoughts(String innerThoughts) { this.innerThoughts = innerThoughts; }
    public String getMemoryTrigger() { return memoryTrigger; }
    public void setMemoryTrigger(String memoryTrigger) { this.memoryTrigger = memoryTrigger; }
    public Boolean getDisturbing() { return disturbing; }
    public void setDisturbing(Boolean disturbing) { this.disturbing = disturbing; }
    public List<Choice> getChoices() { return choices; }
    public void setChoices(List<Choice> choices) { this.choices = choices; }
    public List<Interactable> getInteractables() { return interactables; }
    public void setInteractables(List<Interactable> interactables) { this.interactables = interactables; }
    public Map<String, Integer> getVariableEffects() { return variableEffects; }
    public void setVariableEffects(Map<String, Integer> variableEffects) { this.variableEffects = variableEffects; }
    public Map<String, String> getAmbientEffects() { return ambientEffects; }
    public void setAmbientEffects(Map<String, String> ambientEffects) { this.ambientEffects = ambientEffects; }
}
