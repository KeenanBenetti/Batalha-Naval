package com.batalhaNaval.Model;

public class GameState {
    String VezDe;
    String GameStatus;

    public GameState(String VezDe, String GameStatus) {
        this.VezDe = VezDe;
        this.GameStatus = GameStatus;
    }

    public String getVezDe() {
        return VezDe;
    }

    public String getGameStatus() {
        return GameStatus;
    }

    public void setVezDe(String vezDe) {
        VezDe = vezDe;
    }
}
