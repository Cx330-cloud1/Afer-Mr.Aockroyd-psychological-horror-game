package com.roger.mystery.controller;

import com.roger.mystery.model.*;
import com.roger.mystery.service.GameEngineService;
import com.roger.mystery.service.GameStateService;
import com.roger.mystery.service.StoryLoaderService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("/api/game")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class GameController {
    private final GameEngineService gameEngine;
    private final GameStateService stateService;
    private final StoryLoaderService storyLoader;

    public GameController(GameEngineService gameEngine, GameStateService stateService, StoryLoaderService storyLoader) {
        this.gameEngine = gameEngine;
        this.stateService = stateService;
        this.storyLoader = storyLoader;
    }

    @PostMapping("/start")
    public ResponseEntity<ApiResponse<GameResponse>> start(HttpSession session) {
        stateService.createNewGame(session.getId());
        return ResponseEntity.ok(ApiResponse.success(gameEngine.current(session.getId())));
    }

    @GetMapping("/state")
    public ResponseEntity<ApiResponse<GameResponse>> state(HttpSession session) {
        return ResponseEntity.ok(ApiResponse.success(gameEngine.current(session.getId())));
    }

    @PostMapping("/choice")
    public ResponseEntity<ApiResponse<GameResponse>> choice(@Valid @RequestBody PlayerChoice choice, HttpSession session) {
        GameResponse response = gameEngine.processChoice(session.getId(), choice);
        return ResponseEntity.ok(response.getType() == GameResponse.ResponseType.ERROR ? ApiResponse.error(response.getMessage()) : ApiResponse.success(response));
    }

    @PostMapping("/interact")
    public ResponseEntity<ApiResponse<InteractionResponse>> interact(@Valid @RequestBody InteractionRequest request, HttpSession session) {
        InteractionResponse response = gameEngine.handleInteraction(session.getId(), request);
        return ResponseEntity.ok(response.isSuccess() ? ApiResponse.success(response) : ApiResponse.error(response.getMessage()));
    }

    @PostMapping("/quicktime")
    public ResponseEntity<ApiResponse<GameResponse>> quicktime(@RequestBody QuickTimeRequest request, HttpSession session) {
        return ResponseEntity.ok(ApiResponse.success(gameEngine.handleQuickTime(session.getId(), request)));
    }

    @GetMapping("/memories")
    public ResponseEntity<ApiResponse<Collection<MemoryFragment>>> memories() {
        return ResponseEntity.ok(ApiResponse.success(storyLoader.getMemories("roger_mystery")));
    }

    @PostMapping("/save")
    public ResponseEntity<ApiResponse<Map<String, String>>> save(HttpSession session) {
        return ResponseEntity.ok(ApiResponse.success(Map.of("saveId", stateService.saveSnapshot(session.getId()))));
    }

    @PostMapping("/load")
    public ResponseEntity<ApiResponse<GameState>> load(@RequestBody Map<String, String> body, HttpSession session) {
        return ResponseEntity.ok(ApiResponse.success(stateService.loadSnapshot(body.get("saveId"), session.getId())));
    }

    @PostMapping("/restart")
    public ResponseEntity<ApiResponse<GameResponse>> restart(HttpSession session) {
        stateService.resetGame(session.getId());
        return start(session);
    }
}
