package com.roger.mystery.model;

import java.util.ArrayList;
import java.util.List;

public class InteractionResponse {
    private boolean success;
    private String message;
    private String description;
    private GameState state;
    private MemoryFragment triggeredMemory;
    private List<Flashback> flashbacks = new ArrayList<>();
    private Interactable interactable;

    public static InteractionResponse success(String description, GameState state, Interactable interactable) { InteractionResponse r = new InteractionResponse(); r.success = true; r.description = description; r.state = state; r.interactable = interactable; return r; }
    public static InteractionResponse error(String message) { InteractionResponse r = new InteractionResponse(); r.success = false; r.message = message; return r; }
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public GameState getState() { return state; }
    public void setState(GameState state) { this.state = state; }
    public MemoryFragment getTriggeredMemory() { return triggeredMemory; }
    public void setTriggeredMemory(MemoryFragment triggeredMemory) { this.triggeredMemory = triggeredMemory; }
    public List<Flashback> getFlashbacks() { return flashbacks; }
    public void setFlashbacks(List<Flashback> flashbacks) { this.flashbacks = flashbacks; }
    public Interactable getInteractable() { return interactable; }
    public void setInteractable(Interactable interactable) { this.interactable = interactable; }
}
