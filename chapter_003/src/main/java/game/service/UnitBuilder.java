package game.service;

import game.model.Unit;

public class UnitBuilder {
    private GameLog gameLog;
    private final int DAMAGE_RANGE_FROM = 1;
    private final int DAMAGE_RANGE_TO = 5;
    private final int HEALTH_RANGE_FROM = 15;
    private final int HEALTH_RANGE_TO = 20;
    private final String EVENT_BUILDER = "Created player: %s";

    public UnitBuilder(GameLog gameLog) {
        this.gameLog = gameLog;
    }

    private int generateDamageCount() {
        return DAMAGE_RANGE_FROM + (int) (Math.random() * DAMAGE_RANGE_TO);
    }

    private int generateRangeCount() {
        return HEALTH_RANGE_FROM + (int) (Math.random() * HEALTH_RANGE_TO);
    }

    public Unit build(String name) {
        Unit unit = new Unit();
        unit.setName(name);
        unit.setDamage(generateDamageCount());
        unit.setHealth(generateRangeCount());
        gameLog.addEvent(String.format(EVENT_BUILDER, unit.toString()));
        return unit;
    }
}
