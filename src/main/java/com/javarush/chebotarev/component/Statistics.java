package com.javarush.chebotarev.component;

public class Statistics {

    private int victoriesCount = 0;
    private int defeatsCount = 0;

    public int getVictoriesCount() {
        return victoriesCount;
    }

    public int getDefeatsCount() {
        return defeatsCount;
    }

    public int getGamesCount() {
        return (victoriesCount + defeatsCount);
    }

    public void incVictories() {
        victoriesCount++;
    }

    public void incDefeats() {
        defeatsCount++;
    }
}
