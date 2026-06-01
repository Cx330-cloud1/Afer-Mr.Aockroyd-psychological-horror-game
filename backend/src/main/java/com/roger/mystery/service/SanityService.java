package com.roger.mystery.service;

import com.roger.mystery.model.GameState;
import org.springframework.stereotype.Service;

@Service
public class SanityService {
    public void applySanityCost(GameState state, int cost) { state.setSanity(state.getSanity() - Math.max(0, cost)); }
    public boolean shouldTriggerRandomFlashback(GameState state) { return state.getSanity() < 60 && state.getSelfAwareness() >= 20; }
    public String sanityLevel(GameState state) {
        int s = state.getSanity();
        if (s >= 70) return "HIGH";
        if (s >= 40) return "MEDIUM";
        if (s >= 20) return "LOW";
        return "CRITICAL";
    }
}
