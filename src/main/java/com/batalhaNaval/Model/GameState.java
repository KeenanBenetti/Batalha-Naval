package com.batalhaNaval.Model;

public class GameState {
    String VezDe;
    String GameStatus;
    String Vencedor;

    public GameState(String VezDe, String GameStatus) {
        this.VezDe = VezDe;
        this.GameStatus = GameStatus;
        this.Vencedor = null;
    }

    public String getVezDe() {
        return VezDe;
    }

    public String getGameStatus() {
        return GameStatus;
    }

    public void setGameStatus(String gameStatus) {
        GameStatus = gameStatus;
    }

    public void setVencedor(String vencedor) {
        Vencedor = vencedor;
    }

    public void setVezDe(String vezDe) {
        VezDe = vezDe;
    }
}
