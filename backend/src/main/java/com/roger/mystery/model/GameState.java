package com.roger.mystery.model;

import java.time.LocalDateTime;
import java.util.*;

public class GameState {
    private String sessionId;
    private String currentStoryId = "roger_mystery";
    private String currentNodeId = "node_1_1";
    private Integer selfAwareness = 0;
    private Integer narratorReliability = 100;
    private Integer poirotSuspicion = 0;
    private Integer sanity = 100;
    private Set<String> collectedMemories = new LinkedHashSet<>();
    private Set<String> foundClues = new LinkedHashSet<>();
    private Map<String, Integer> characterRelations = new LinkedHashMap<>();
    private Map<String, Boolean> interacted = new LinkedHashMap<>();
    private List<String> choiceHistory = new ArrayList<>();
    private LocalDateTime startTime = LocalDateTime.now();
    private Integer elapsedMinutes = 0;
    private Map<String, QuickTimeResult> quickTimeResults = new LinkedHashMap<>();

    public void clamp() {
        selfAwareness = clamp100(selfAwareness);
        narratorReliability = clamp100(narratorReliability);
        poirotSuspicion = clamp100(poirotSuspicion);
        sanity = clamp100(sanity);
        characterRelations.replaceAll((k, v) -> clamp100(v));
    }
    private int clamp100(Integer v) { return Math.max(0, Math.min(100, v == null ? 0 : v)); }

    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }
    public String getCurrentStoryId() { return currentStoryId; }
    public void setCurrentStoryId(String currentStoryId) { this.currentStoryId = currentStoryId; }
    public String getCurrentNodeId() { return currentNodeId; }
    public void setCurrentNodeId(String currentNodeId) { this.currentNodeId = currentNodeId; }
    public Integer getSelfAwareness() { return selfAwareness; }
    public void setSelfAwareness(Integer selfAwareness) { this.selfAwareness = selfAwareness; clamp(); }
    public Integer getNarratorReliability() { return narratorReliability; }
    public void setNarratorReliability(Integer narratorReliability) { this.narratorReliability = narratorReliability; clamp(); }
    public Integer getPoirotSuspicion() { return poirotSuspicion; }
    public void setPoirotSuspicion(Integer poirotSuspicion) { this.poirotSuspicion = poirotSuspicion; clamp(); }
    public Integer getSanity() { return sanity; }
    public void setSanity(Integer sanity) { this.sanity = sanity; clamp(); }
    public Set<String> getCollectedMemories() { return collectedMemories; }
    public void setCollectedMemories(Set<String> collectedMemories) { this.collectedMemories = collectedMemories; }
    public Set<String> getFoundClues() { return foundClues; }
    public void setFoundClues(Set<String> foundClues) { this.foundClues = foundClues; }
    public Map<String, Integer> getCharacterRelations() { return characterRelations; }
    public void setCharacterRelations(Map<String, Integer> characterRelations) { this.characterRelations = characterRelations; }
    public Map<String, Boolean> getInteracted() { return interacted; }
    public void setInteracted(Map<String, Boolean> interacted) { this.interacted = interacted; }
    public List<String> getChoiceHistory() { return choiceHistory; }
    public void setChoiceHistory(List<String> choiceHistory) { this.choiceHistory = choiceHistory; }
    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }
    public Integer getElapsedMinutes() { return elapsedMinutes; }
    public void setElapsedMinutes(Integer elapsedMinutes) { this.elapsedMinutes = elapsedMinutes; }
    public Map<String, QuickTimeResult> getQuickTimeResults() { return quickTimeResults; }
    public void setQuickTimeResults(Map<String, QuickTimeResult> quickTimeResults) { this.quickTimeResults = quickTimeResults; }
}
