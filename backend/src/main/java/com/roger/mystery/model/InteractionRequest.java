package com.roger.mystery.model;

import jakarta.validation.constraints.NotBlank;

public class InteractionRequest {
    @NotBlank
    private String interactableId;
    private String action = "EXAMINE";
    public String getInteractableId() { return interactableId; }
    public void setInteractableId(String interactableId) { this.interactableId = interactableId; }
    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }
}
