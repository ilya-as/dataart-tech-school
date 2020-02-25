package game.service;

import game.model.Unit;
import game.service.GameLog;

public class GameService {
    private GameLog gameLog;
    private final String EVENT_START_GAME = "Game started!";
    private final String FIGHT_EVENT = "Player %s damaged %s by %d count.Player %s has %d health left";
    private final String FIGHT_END = "The battle is over! Player %s is win!";

    public GameService(GameLog gameLog) {
        this.gameLog = gameLog;
    }

    public void fight(Unit unit1, Unit unit2) {
        gameLog.addEvent(EVENT_START_GAME);
        boolean turn = true;
        do {
            Unit master = turn ? unit1 : unit2;
            Unit opponent = !turn ? unit1 : unit2;
            int healthLeft = opponent.getHealth() - master.getDamage();
            opponent.setHealth(healthLeft < Unit.HEALTH_BORDER ? Unit.HEALTH_BORDER : healthLeft);
            gameLog.addEvent(String.format(FIGHT_EVENT, master.getName(), opponent.getName(),
                    master.getDamage(), opponent.getName(), opponent.getHealth()));
            turn = !turn;
        } while (unit1.isAlive() && unit2.isAlive());
        Unit winner = unit1.isAlive() ? unit1 : unit2;
        gameLog.addEvent(String.format(FIGHT_END, winner.getName()));
    }
}
