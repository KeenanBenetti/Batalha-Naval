package com.batalhaNaval.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class GameState {
    StringProperty VezDe = new SimpleStringProperty();
    String Jogador1Nome;
    String Jogador2Nome;
    int SetupPlayersReady;
    StringProperty GameStatus = new SimpleStringProperty();
    String Vencedor;

    public GameState(String VezDe, String Jogador1Nome, String Jogador2Nome, String GameStatus) {
        this.VezDe.set(VezDe);
        this.Jogador1Nome = Jogador1Nome;
        this.Jogador2Nome = Jogador2Nome;
        this.SetupPlayersReady = 0;
        this.GameStatus.set(GameStatus);
        this.Vencedor = null;
    }

    public StringProperty getVezDe() {
        return VezDe;
    }

    public String getJogador1Nome() {
        return Jogador1Nome;
    }

    public String getJogador2Nome() {
        return Jogador2Nome;
    }

    public void setVezDe(String vezDe) {
        VezDe.set(vezDe);
    }

    public StringProperty getGameStatus() {
        return GameStatus;
    }

    public void setGameStatus(String gameStatus) {
        GameStatus.set(gameStatus);
    }

    public void setVencedor(String vencedor) {
        Vencedor = vencedor;
    }

    public int getSetupPlayersReady() {
        return SetupPlayersReady;
    }

    public void setSetupPlayersReady(int setupPlayersReady) {
        SetupPlayersReady = setupPlayersReady;
    }
}
