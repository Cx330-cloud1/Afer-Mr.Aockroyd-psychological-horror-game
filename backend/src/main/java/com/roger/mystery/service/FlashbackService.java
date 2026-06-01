package com.roger.mystery.service;

import com.roger.mystery.model.Flashback;
import com.roger.mystery.model.FlashbackTrigger;
import com.roger.mystery.model.GameState;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FlashbackService {
    private final StoryLoaderService storyLoader;
    private final ConditionEvaluator evaluator;
    private final Random random = new Random();

    public FlashbackService(StoryLoaderService storyLoader, ConditionEvaluator evaluator) {
        this.storyLoader = storyLoader;
        this.evaluator = evaluator;
    }

    public List<Flashback> checkChoiceTriggers(GameState state, String nodeId, String choiceId) {
        return storyLoader.getTriggers(state.getCurrentStoryId()).stream()
                .filter(t -> t.getType() == FlashbackTrigger.TriggerType.CHOICE_SELECTED || t.getType() == FlashbackTrigger.TriggerType.VARIABLE_THRESHOLD || t.getType() == FlashbackTrigger.TriggerType.PROGRESS_MILESTONE)
                .filter(t -> t.getTargetNodeId() == null || t.getTargetNodeId().equals(nodeId))
                .filter(t -> t.getTargetChoiceId() == null || t.getTargetChoiceId().equals(choiceId))
                .filter(t -> evaluator.allMatch(state, t.getConditions()))
                .map(t -> storyLoader.getFlashback(state.getCurrentStoryId(), t.getFlashbackId()).orElse(null))
                .filter(Objects::nonNull)
                .filter(f -> state.getSelfAwareness() >= defaultInt(f.getMinSelfAwareness()))
                .collect(Collectors.toList());
    }

    public List<Flashback> checkInteractableTriggers(GameState state, String nodeId, String interactableId) {
        return storyLoader.getTriggers(state.getCurrentStoryId()).stream()
                .filter(t -> t.getType() == FlashbackTrigger.TriggerType.ITEM_INTERACTED)
                .filter(t -> t.getTargetNodeId() == null || t.getTargetNodeId().equals(nodeId))
                .filter(t -> t.getTargetInteractableId() == null || t.getTargetInteractableId().equals(interactableId))
                .filter(t -> evaluator.allMatch(state, t.getConditions()))
                .map(t -> storyLoader.getFlashback(state.getCurrentStoryId(), t.getFlashbackId()).orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public Optional<Flashback> getRandomFlashback(GameState state) {
        List<Flashback> available = storyLoader.getFlashbacks(state.getCurrentStoryId()).stream()
                .filter(f -> Boolean.TRUE.equals(f.getDisturbing()))
                .filter(f -> state.getSelfAwareness() >= defaultInt(f.getMinSelfAwareness()))
                .toList();
        return available.isEmpty() ? Optional.empty() : Optional.of(available.get(random.nextInt(available.size())));
    }

    public Optional<Flashback> getPressureFlashback(GameState state) {
        List<Flashback> available = storyLoader.getFlashbacks(state.getCurrentStoryId()).stream()
                .filter(f -> f.getType() == Flashback.FlashbackType.HAND_TREMOR || f.getType() == Flashback.FlashbackType.SCREAM_ECHO)
                .toList();
        return available.isEmpty() ? Optional.empty() : Optional.of(available.get(random.nextInt(available.size())));
    }

    private int defaultInt(Integer value) { return value == null ? 0 : value; }
}
