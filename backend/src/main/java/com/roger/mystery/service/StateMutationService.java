package com.roger.mystery.service;

import com.roger.mystery.model.GameState;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class StateMutationService {
    public void applyEffects(GameState state, Map<String, Integer> effects) {
        if (effects == null) return;
        effects.forEach((key, delta) -> applyEffect(state, key, delta == null ? 0 : delta));
        state.clamp();
    }

    public void applyEffect(GameState state, String key, int delta) {
        switch (key) {
            case "selfAwareness" -> state.setSelfAwareness(state.getSelfAwareness() + delta);
            case "narratorReliability" -> state.setNarratorReliability(state.getNarratorReliability() + delta);
            case "poirotSuspicion" -> state.setPoirotSuspicion(state.getPoirotSuspicion() + delta);
            case "sanity" -> state.setSanity(state.getSanity() + delta);
            default -> applyDotted(state, key, delta);
        }
    }

    private void applyDotted(GameState state, String key, int delta) {
        if (key.startsWith("characterRelations.")) {
            String name = key.substring(19);
            state.getCharacterRelations().merge(name, delta, Integer::sum);
        } else if (key.startsWith("foundClues.")) {
            if (delta > 0) state.getFoundClues().add(key.substring(11));
            else state.getFoundClues().remove(key.substring(11));
        } else if (key.startsWith("interacted.")) {
            state.getInteracted().put(key.substring(11), delta > 0);
        }
    }
}
