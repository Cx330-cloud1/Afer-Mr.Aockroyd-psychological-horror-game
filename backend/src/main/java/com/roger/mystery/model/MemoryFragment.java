package com.roger.mystery.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class MemoryFragment {
    private String id;
    private String title;
    private String content;
    private MemoryType type = MemoryType.TRIGGERED;
    private String triggerNodeId;
    private Map<String, Condition> unlockConditions = new LinkedHashMap<>();
    private Integer selfAwarenessReward = 0;

    public enum MemoryType { TRIGGERED, COLLECTIBLE, HIDDEN }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public MemoryType getType() { return type; }
    public void setType(MemoryType type) { this.type = type; }
    public String getTriggerNodeId() { return triggerNodeId; }
    public void setTriggerNodeId(String triggerNodeId) { this.triggerNodeId = triggerNodeId; }
    public Map<String, Condition> getUnlockConditions() { return unlockConditions; }
    public void setUnlockConditions(Map<String, Condition> unlockConditions) { this.unlockConditions = unlockConditions; }
    public Integer getSelfAwarenessReward() { return selfAwarenessReward; }
    public void setSelfAwarenessReward(Integer selfAwarenessReward) { this.selfAwarenessReward = selfAwarenessReward; }
}
