package game;

import game.model.Unit;
import game.service.GameLog;
import game.service.GameService;

public class Game {
    private final int DAMAGE_RANGE_FROM = 1;
    private final int DAMAGE_RANGE_TO = 5;
    private final int HEALTH_RANGE_FROM = 15;
    private final int HEALTH_RANGE_TO = 20;
    private final String EVENT_BUILDER = "Created player: %s";

    public void startGame() {
        GameLog gameLog = new GameLog();
        Unit unit1 = new Unit("unit2", generateHealthCount(), generateDamageCount());
        gameLog.addEvent(String.format(EVENT_BUILDER, unit1.toString()));
        Unit unit2 = new Unit("unit2", generateHealthCount(), generateDamageCount());
        gameLog.addEvent(String.format(EVENT_BUILDER, unit2.toString()));
        GameService gameService = new GameService(gameLog);
        gameService.fight(unit1, unit2);
        gameLog.printLog();
    }

    private int generateDamageCount() {
        return DAMAGE_RANGE_FROM + (int) (Math.random() * DAMAGE_RANGE_TO);
    }

    private int generateHealthCount() {
        return HEALTH_RANGE_FROM + (int) (Math.random() * HEALTH_RANGE_TO);
    }
}
