package com.roger.mystery.service;

import com.roger.mystery.model.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameEngineService {
    private final StoryLoaderService storyLoader;
    private final GameStateService stateService;
    private final ConditionEvaluator evaluator;
    private final StateMutationService mutator;
    private final MemoryService memoryService;
    private final FlashbackService flashbackService;
    private final SanityService sanityService;
    private final EndingService endingService;

    public GameEngineService(StoryLoaderService storyLoader, GameStateService stateService, ConditionEvaluator evaluator,
                             StateMutationService mutator, MemoryService memoryService, FlashbackService flashbackService,
                             SanityService sanityService, EndingService endingService) {
        this.storyLoader = storyLoader;
        this.stateService = stateService;
        this.evaluator = evaluator;
        this.mutator = mutator;
        this.memoryService = memoryService;
        this.flashbackService = flashbackService;
        this.sanityService = sanityService;
        this.endingService = endingService;
    }

    public GameResponse current(String sessionId) {
        GameState state = stateService.getGameState(sessionId);
        StoryNode node = storyLoader.getNode(state.getCurrentStoryId(), state.getCurrentNodeId());
        return GameResponse.node(node, state, List.of(), List.of());
    }

    public GameResponse processChoice(String sessionId, PlayerChoice playerChoice) {
        GameState state = stateService.getGameState(sessionId);
        StoryNode currentNode = storyLoader.getNode(state.getCurrentStoryId(), state.getCurrentNodeId());
        Choice selected = currentNode.getChoices().stream()
                .filter(c -> c.getId().equals(playerChoice.getChoiceId()))
                .filter(c -> evaluator.allMatch(state, c.getRequirements()))
                .findFirst().orElse(null);
        if (selected == null) return GameResponse.error("无效的选择或条件不满足");

        List<Flashback> flashbacks = new ArrayList<>(flashbackService.checkChoiceTriggers(state, currentNode.getId(), selected.getId()));
        if (selected.getFlashbackTrigger() != null) storyLoader.getFlashback(state.getCurrentStoryId(), selected.getFlashbackTrigger()).ifPresent(flashbacks::add);

        applyQuickTimePressureIfNeeded(state, selected, playerChoice);
        mutator.applyEffects(state, selected.getEffects());
        state.getChoiceHistory().add(selected.getId());
        state.setElapsedMinutes(state.getElapsedMinutes() + 5);

        for (Flashback flashback : flashbacks) {
            sanityService.applySanityCost(state, flashback.getSanityCost() == null ? 0 : flashback.getSanityCost());
            if (flashback.getUnlockMemoryId() != null) memoryService.unlockById(state, flashback.getUnlockMemoryId());
        }
        if (sanityService.shouldTriggerRandomFlashback(state)) flashbackService.getRandomFlashback(state).ifPresent(flashbacks::add);

        String nextNodeId = selected.getNextNodeId() == null ? currentNode.getId() : selected.getNextNodeId();
        state.setCurrentNodeId(nextNodeId);
        List<MemoryFragment> memories = memoryService.unlockEligibleMemories(state, nextNodeId);
        state.clamp();
        stateService.saveGame(state);

        if (nextNodeId.startsWith("ending_") || "node_5_1".equals(nextNodeId)) return GameResponse.ending(endingService.determineEnding(state, selected.getId()), state);
        return GameResponse.node(storyLoader.getNode(state.getCurrentStoryId(), nextNodeId), state, memories, flashbacks);
    }

    public InteractionResponse handleInteraction(String sessionId, InteractionRequest request) {
        GameState state = stateService.getGameState(sessionId);
        StoryNode node = storyLoader.getNode(state.getCurrentStoryId(), state.getCurrentNodeId());
        Interactable interactable = node.getInteractables().stream().filter(i -> i.getId().equals(request.getInteractableId())).findFirst().orElse(null);
        if (interactable == null) return InteractionResponse.error("交互对象不存在");
        if (!isVisible(state, interactable)) return InteractionResponse.error("当前无法检查这个对象");

        state.getInteracted().put(interactable.getId(), true);
        if (interactable.getClueId() != null) state.getFoundClues().add(interactable.getClueId());
        mutator.applyEffects(state, interactable.getEffects());
        InteractionResponse response = InteractionResponse.success(interactable.getDescription(), state, interactable);
        if (interactable.getMemoryId() != null) memoryService.unlockById(state, interactable.getMemoryId()).ifPresent(response::setTriggeredMemory);
        List<Flashback> flashbacks = flashbackService.checkInteractableTriggers(state, node.getId(), interactable.getId());
        for (Flashback f : flashbacks) sanityService.applySanityCost(state, f.getSanityCost() == null ? 0 : f.getSanityCost());
        response.setFlashbacks(flashbacks);
        state.clamp();
        stateService.saveGame(state);
        return response;
    }

    public GameResponse handleQuickTime(String sessionId, QuickTimeRequest request) {
        GameState state = stateService.getGameState(sessionId);
        state.getQuickTimeResults().put(request.getEventId(), QuickTimeResult.of(request.getChoiceId(), request.getTimeTaken()));
        int taken = request.getTimeTaken() == null ? 0 : request.getTimeTaken();
        int limit = request.getTimeLimit() == null ? 10 : request.getTimeLimit();
        if (taken <= 3) state.setNarratorReliability(state.getNarratorReliability() + 5);
        if (taken > limit) { state.setNarratorReliability(state.getNarratorReliability() - 10); state.setPoirotSuspicion(state.getPoirotSuspicion() + 5); }
        List<Flashback> flashbacks = new ArrayList<>();
        if (taken > limit * 0.7) flashbackService.getPressureFlashback(state).ifPresent(flashbacks::add);
        return GameResponse.node(storyLoader.getNode(state.getCurrentStoryId(), state.getCurrentNodeId()), state, List.of(), flashbacks);
    }

    public boolean isVisible(GameState state, Interactable interactable) {
        if (Boolean.TRUE.equals(interactable.getHidden()) && !evaluator.allMatch(state, interactable.getVisibilityConditions())) return false;
        return evaluator.allMatch(state, interactable.getVisibilityConditions());
    }

    private void applyQuickTimePressureIfNeeded(GameState state, Choice selected, PlayerChoice playerChoice) {
        if (!Boolean.TRUE.equals(selected.getQuickTimeEvent()) || playerChoice.getTimeTaken() == null) return;
        int taken = playerChoice.getTimeTaken();
        int limit = selected.getTimeLimit() == null ? 10 : selected.getTimeLimit();
        state.getQuickTimeResults().put(selected.getId(), QuickTimeResult.of(selected.getId(), taken));
        if (taken <= 3) state.setNarratorReliability(state.getNarratorReliability() + 5);
        if (taken > limit) { state.setNarratorReliability(state.getNarratorReliability() - 10); state.setPoirotSuspicion(state.getPoirotSuspicion() + 8); sanityService.applySanityCost(state, 10); }
        else if (taken > limit * 0.7) { sanityService.applySanityCost(state, 5); }
    }
}
