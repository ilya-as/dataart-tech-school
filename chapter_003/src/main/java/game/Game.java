package game;

import game.model.Unit;
import game.service.GameLog;
import game.service.GameService;
import game.service.UnitBuilder;

public class Game {
    public void startGame() {
        GameLog gameLog = new GameLog();
        UnitBuilder unitBuilder = new UnitBuilder(gameLog);
        Unit unit1 = unitBuilder.build("unit1");
        Unit unit2 = unitBuilder.build("unit2");
        GameService gameService = new GameService(gameLog);
        gameService.fight(unit1, unit2);
        gameLog.printLog();
    }
}
