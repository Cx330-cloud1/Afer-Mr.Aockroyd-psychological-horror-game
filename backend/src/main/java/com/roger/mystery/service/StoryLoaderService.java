package com.roger.mystery.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roger.mystery.model.Flashback;
import com.roger.mystery.model.FlashbackTrigger;
import com.roger.mystery.model.MemoryFragment;
import com.roger.mystery.model.StoryNode;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class StoryLoaderService {
    private final ObjectMapper objectMapper;
    private final Map<String, Map<String, StoryNode>> storyCache = new ConcurrentHashMap<>();
    private final Map<String, Map<String, MemoryFragment>> memoryCache = new ConcurrentHashMap<>();
    private final Map<String, Map<String, Flashback>> flashbackCache = new ConcurrentHashMap<>();
    private final Map<String, List<FlashbackTrigger>> triggerCache = new ConcurrentHashMap<>();

    public StoryLoaderService(ObjectMapper objectMapper) { this.objectMapper = objectMapper; }

    @PostConstruct
    public void init() { loadStory("roger_mystery"); }

    public void loadStory(String storyId) {
        String base = "stories/" + storyId + "/";
        List<StoryNode> nodes = read(base + "story_nodes.json", new TypeReference<>() {});
        List<MemoryFragment> memories = read(base + "memory_fragments.json", new TypeReference<>() {});
        FlashbackFile flashbacks = read(base + "flashback_definitions.json", new TypeReference<>() {});
        storyCache.put(storyId, nodes.stream().collect(Collectors.toMap(StoryNode::getId, Function.identity(), (a, b) -> a, LinkedHashMap::new)));
        memoryCache.put(storyId, memories.stream().collect(Collectors.toMap(MemoryFragment::getId, Function.identity(), (a, b) -> a, LinkedHashMap::new)));
        flashbackCache.put(storyId, flashbacks.flashbacks.stream().collect(Collectors.toMap(Flashback::getId, Function.identity(), (a, b) -> a, LinkedHashMap::new)));
        triggerCache.put(storyId, flashbacks.triggers);
    }

    public StoryNode getNode(String storyId, String nodeId) {
        StoryNode node = storyCache.getOrDefault(storyId, Map.of()).get(nodeId);
        if (node == null) throw new IllegalArgumentException("故事节点不存在: " + nodeId);
        return node;
    }

    public List<StoryNode> getNodes(String storyId) { return new ArrayList<>(storyCache.getOrDefault(storyId, Map.of()).values()); }
    public Collection<MemoryFragment> getMemories(String storyId) { return memoryCache.getOrDefault(storyId, Map.of()).values(); }
    public Optional<MemoryFragment> getMemory(String storyId, String memoryId) { return Optional.ofNullable(memoryCache.getOrDefault(storyId, Map.of()).get(memoryId)); }
    public Collection<Flashback> getFlashbacks(String storyId) { return flashbackCache.getOrDefault(storyId, Map.of()).values(); }
    public Optional<Flashback> getFlashback(String storyId, String flashbackId) { return Optional.ofNullable(flashbackCache.getOrDefault(storyId, Map.of()).get(flashbackId)); }
    public List<FlashbackTrigger> getTriggers(String storyId) { return triggerCache.getOrDefault(storyId, List.of()); }

    private <T> T read(String path, TypeReference<T> ref) {
        try (InputStream in = new ClassPathResource(path).getInputStream()) {
            return objectMapper.readValue(in, ref);
        } catch (Exception e) {
            throw new IllegalStateException("无法加载资源: " + path, e);
        }
    }

    public static class FlashbackFile {
        public List<Flashback> flashbacks = new ArrayList<>();
        public List<FlashbackTrigger> triggers = new ArrayList<>();
    }
}
