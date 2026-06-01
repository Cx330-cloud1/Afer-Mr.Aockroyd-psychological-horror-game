package com.roger.mystery.service;

import com.roger.mystery.model.GameState;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GameStateService {
    private final Map<String, GameState> sessions = new ConcurrentHashMap<>();
    private final Map<String, GameState> saves = new ConcurrentHashMap<>();

    public GameState getGameState(String sessionId) { return sessions.computeIfAbsent(sessionId, this::createNewGame); }

    public GameState createNewGame(String sessionId) {
        GameState state = new GameState();
        state.setSessionId(sessionId);
        state.setCurrentStoryId("roger_mystery");
        state.setCurrentNodeId("node_1_1");
        state.setStartTime(LocalDateTime.now());
        Map<String, Integer> rel = new LinkedHashMap<>();
        rel.put("poirot", 50); rel.put("flora", 60); rel.put("ralph", 40); rel.put("parker", 30); rel.put("roger", 55);
        state.setCharacterRelations(rel);
        sessions.put(sessionId, state);
        return state;
    }

    public void saveGame(GameState state) { sessions.put(state.getSessionId(), state); }
    public String saveSnapshot(String sessionId) { String saveId = sessionId + "-save"; saves.put(saveId, getGameState(sessionId)); return saveId; }
    public GameState loadSnapshot(String saveId, String sessionId) { GameState state = saves.get(saveId); if (state == null) throw new IllegalArgumentException("存档不存在"); state.setSessionId(sessionId); sessions.put(sessionId, state); return state; }
    public void resetGame(String sessionId) { sessions.remove(sessionId); }
}
