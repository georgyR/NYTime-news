package com.androidacademy.msk.exerciseproject.network.api;

public enum Section {

    HOME,
    OPINION,
    WORLD,
    NATIONAL,
    POLITICS,
    UPSHOT,
    NYREGION,
    BUSINESS,
    TECHNOLOGY,
    SCIENCE,
    HEALTH;

    public String getLowerCaseName() {
        return this.toString().toLowerCase();
    }

    public String getHeadwordName() {
        return this.toString().substring(0, 1) +
                this.toString().substring(1).toLowerCase();
    }

}
