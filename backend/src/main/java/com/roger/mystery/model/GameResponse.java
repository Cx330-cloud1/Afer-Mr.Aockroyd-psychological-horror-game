package com.roger.mystery.model;

import java.util.ArrayList;
import java.util.List;

public class GameResponse {
    private ResponseType type = ResponseType.NODE;
    private String message;
    private StoryNode node;
    private GameState state;
    private List<MemoryFragment> triggeredMemories = new ArrayList<>();
    private List<Flashback> flashbacks = new ArrayList<>();
    private Ending ending;

    public enum ResponseType { NODE, FLASHBACK, ENDING, ERROR }
    public static GameResponse node(StoryNode node, GameState state, List<MemoryFragment> memories, List<Flashback> flashbacks) {
        GameResponse r = new GameResponse(); r.node = node; r.state = state; r.triggeredMemories = memories; r.flashbacks = flashbacks; return r;
    }
    public static GameResponse ending(Ending ending, GameState state) { GameResponse r = new GameResponse(); r.type = ResponseType.ENDING; r.ending = ending; r.state = state; return r; }
    public static GameResponse error(String message) { GameResponse r = new GameResponse(); r.type = ResponseType.ERROR; r.message = message; return r; }
    public ResponseType getType() { return type; }
    public void setType(ResponseType type) { this.type = type; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public StoryNode getNode() { return node; }
    public void setNode(StoryNode node) { this.node = node; }
    public GameState getState() { return state; }
    public void setState(GameState state) { this.state = state; }
    public List<MemoryFragment> getTriggeredMemories() { return triggeredMemories; }
    public void setTriggeredMemories(List<MemoryFragment> triggeredMemories) { this.triggeredMemories = triggeredMemories; }
    public List<Flashback> getFlashbacks() { return flashbacks; }
    public void setFlashbacks(List<Flashback> flashbacks) { this.flashbacks = flashbacks; }
    public Ending getEnding() { return ending; }
    public void setEnding(Ending ending) { this.ending = ending; }
}
