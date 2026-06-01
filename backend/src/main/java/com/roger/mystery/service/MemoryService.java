package com.roger.mystery.service;

import com.roger.mystery.model.GameState;
import com.roger.mystery.model.MemoryFragment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemoryService {
    private final StoryLoaderService storyLoader;
    private final ConditionEvaluator evaluator;

    public MemoryService(StoryLoaderService storyLoader, ConditionEvaluator evaluator) {
        this.storyLoader = storyLoader;
        this.evaluator = evaluator;
    }

    public List<MemoryFragment> unlockEligibleMemories(GameState state, String nodeId) {
        List<MemoryFragment> unlocked = new ArrayList<>();
        for (MemoryFragment memory : storyLoader.getMemories(state.getCurrentStoryId())) {
            if (state.getCollectedMemories().contains(memory.getId())) continue;
            if (memory.getTriggerNodeId() != null && !memory.getTriggerNodeId().equals(nodeId)) continue;
            if (evaluator.allMatch(state, memory.getUnlockConditions())) {
                unlock(state, memory).ifPresent(unlocked::add);
            }
        }
        return unlocked;
    }

    public java.util.Optional<MemoryFragment> unlockById(GameState state, String memoryId) {
        return storyLoader.getMemory(state.getCurrentStoryId(), memoryId).flatMap(memory -> unlock(state, memory));
    }

    private java.util.Optional<MemoryFragment> unlock(GameState state, MemoryFragment memory) {
        if (state.getCollectedMemories().add(memory.getId())) {
            state.setSelfAwareness(state.getSelfAwareness() + (memory.getSelfAwarenessReward() == null ? 0 : memory.getSelfAwarenessReward()));
            return java.util.Optional.of(memory);
        }
        return java.util.Optional.empty();
    }
}
