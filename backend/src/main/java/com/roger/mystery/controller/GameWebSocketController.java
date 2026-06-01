package com.roger.mystery.controller;

import com.roger.mystery.model.GameResponse;
import com.roger.mystery.model.InteractionRequest;
import com.roger.mystery.model.InteractionResponse;
import com.roger.mystery.model.PlayerChoice;
import com.roger.mystery.service.GameEngineService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class GameWebSocketController {
    private final GameEngineService gameEngine;
    public GameWebSocketController(GameEngineService gameEngine) { this.gameEngine = gameEngine; }

    @MessageMapping("/game.choice")
    @SendToUser("/queue/game.update")
    public GameResponse handleChoice(PlayerChoice choice, SimpMessageHeaderAccessor headers) {
        return gameEngine.processChoice(headers.getSessionId(), choice);
    }

    @MessageMapping("/game.interact")
    @SendToUser("/queue/game.interaction")
    public InteractionResponse handleInteraction(InteractionRequest request, SimpMessageHeaderAccessor headers) {
        return gameEngine.handleInteraction(headers.getSessionId(), request);
    }
}
