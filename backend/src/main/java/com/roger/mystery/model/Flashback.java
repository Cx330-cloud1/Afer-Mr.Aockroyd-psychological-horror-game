package com.roger.mystery.model;

public class Flashback {
    private String id;
    private FlashbackType type;
    private String title;
    private String content;
    private String imagePath;
    private String audioPath;
    private Integer duration = 1200;
    private Integer sanityCost = 0;
    private Boolean disturbing = true;
    private String unlockMemoryId;
    private Integer minSelfAwareness = 0;
    private Integer intensity = 5;

    public enum FlashbackType { BLOOD_STAIN, CORPSE_VISION, WEAPON_MEMORY, SCREAM_ECHO, FACELESS_VISION, MIRROR_REFLECTION, HAND_TREMOR, VOICE_WHISPER }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public FlashbackType getType() { return type; }
    public void setType(FlashbackType type) { this.type = type; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
    public String getAudioPath() { return audioPath; }
    public void setAudioPath(String audioPath) { this.audioPath = audioPath; }
    public Integer getDuration() { return duration; }
    public void setDuration(Integer duration) { this.duration = duration; }
    public Integer getSanityCost() { return sanityCost; }
    public void setSanityCost(Integer sanityCost) { this.sanityCost = sanityCost; }
    public Boolean getDisturbing() { return disturbing; }
    public void setDisturbing(Boolean disturbing) { this.disturbing = disturbing; }
    public String getUnlockMemoryId() { return unlockMemoryId; }
    public void setUnlockMemoryId(String unlockMemoryId) { this.unlockMemoryId = unlockMemoryId; }
    public Integer getMinSelfAwareness() { return minSelfAwareness; }
    public void setMinSelfAwareness(Integer minSelfAwareness) { this.minSelfAwareness = minSelfAwareness; }
    public Integer getIntensity() { return intensity; }
    public void setIntensity(Integer intensity) { this.intensity = intensity; }
}
