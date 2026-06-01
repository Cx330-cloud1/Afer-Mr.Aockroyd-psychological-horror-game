package com.roger.mystery.service;

import com.roger.mystery.model.Condition;
import com.roger.mystery.model.GameState;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ConditionEvaluator {
    public boolean allMatch(GameState state, Map<String, Condition> conditions) {
        if (conditions == null || conditions.isEmpty()) return true;
        return conditions.entrySet().stream().allMatch(e -> matches(state, e.getKey(), e.getValue()));
    }

    public boolean matches(GameState state, String variableName, Condition condition) {
        if (condition == null) return true;
        String variable = condition.getVariable() != null ? condition.getVariable() : variableName;
        Object raw = resolve(state, variable);
        Condition.Operator op = condition.getOperator() == null ? Condition.Operator.EQ : condition.getOperator();

        if (op == Condition.Operator.EXISTS) return exists(raw);
        if (op == Condition.Operator.NOT_EXISTS) return !exists(raw);

        if (condition.getBoolValue() != null) {
            boolean actual = Boolean.TRUE.equals(raw);
            return op == Condition.Operator.NE ? actual != condition.getBoolValue() : actual == condition.getBoolValue();
        }

        int actual = raw instanceof Number ? ((Number) raw).intValue() : Boolean.TRUE.equals(raw) ? 1 : 0;
        int expected = condition.getValue() == null ? 0 : condition.getValue();
        return switch (op) {
            case EQ -> actual == expected;
            case NE -> actual != expected;
            case GT -> actual > expected;
            case LT -> actual < expected;
            case GTE -> actual >= expected;
            case LTE -> actual <= expected;
            default -> false;
        };
    }

    private boolean exists(Object raw) {
        if (raw == null) return false;
        if (raw instanceof Boolean b) return b;
        if (raw instanceof Number n) return n.intValue() != 0;
        return true;
    }

    public Object resolve(GameState state, String variable) {
        if (variable == null) return null;
        return switch (variable) {
            case "selfAwareness" -> state.getSelfAwareness();
            case "narratorReliability" -> state.getNarratorReliability();
            case "poirotSuspicion" -> state.getPoirotSuspicion();
            case "sanity" -> state.getSanity();
            default -> resolveDotted(state, variable);
        };
    }

    private Object resolveDotted(GameState state, String variable) {
        if (variable.startsWith("interacted.")) return state.getInteracted().getOrDefault(variable.substring(11), false);
        if (variable.startsWith("foundClues.")) return state.getFoundClues().contains(variable.substring(11));
        if (variable.startsWith("collectedMemories.")) return state.getCollectedMemories().contains(variable.substring(18));
        if (variable.startsWith("characterRelations.")) return state.getCharacterRelations().getOrDefault(variable.substring(19), 0);
        if (variable.startsWith("choiceHistory.")) return state.getChoiceHistory().contains(variable.substring(14));
        return null;
    }
}
