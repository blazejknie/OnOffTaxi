package com.onofftaxi.backend.model;

public enum AvailableService {
    FLY_ME_TO_THE_MOON("Lot na księżyc"),
    SHOPPING("Zrób zakupy"),
    SUPPLY_GAS("Dowóz paliwa"),
    CUSTOM("");

    private final String readableName;

    private AvailableService(String readableName) {
        this.readableName = readableName;
    }

    public String getReadableName() {
        return readableName;
    }
}
