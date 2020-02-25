package game.service;

import java.util.*;

public class GameLog {
    private List<String> arrayLog = new ArrayList<>();

    public void addEvent(String event) {
        arrayLog.add(event);
    }

    public void clearLog() {
        arrayLog.clear();
    }

    public void printLog() {
        arrayLog.forEach(System.out::println);
    }
}
