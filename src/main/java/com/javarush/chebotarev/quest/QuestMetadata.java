package com.javarush.chebotarev.quest;

public class QuestMetadata {

    private final String title;
    private final String path;
    private final boolean isServerQuest;

    public QuestMetadata(String title,
                         String path,
                         boolean isServerQuest) {
        this.title = title;
        this.path = path;
        this.isServerQuest = isServerQuest;
    }

    public String getTitle() {
        return title;
    }

    public String getPath() {
        return path;
    }

    public boolean isServerQuest() {
        return isServerQuest;
    }
}
