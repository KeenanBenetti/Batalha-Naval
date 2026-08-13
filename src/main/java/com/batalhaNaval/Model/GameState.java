package com.batalhaNaval.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class GameState {
    StringProperty VezDe = new SimpleStringProperty();
    String Jogador1Nome;
    String Jogador2Nome;
    int SetupPlayersReady;
    StringProperty GameStatus = new SimpleStringProperty();
    StringProperty Vencedor = new SimpleStringProperty();
    StringProperty gamemode = new SimpleStringProperty();

    public GameState() {
        this.SetupPlayersReady = 0;
        this.GameStatus.set("Setup");
        this.Vencedor.set("");
        this.gamemode.set("menu");
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
        Vencedor.set(vencedor);
    }

    public StringProperty getVencedor() {
        return Vencedor;
    }

    public int getSetupPlayersReady() {
        return SetupPlayersReady;
    }

    public void setSetupPlayersReady(int setupPlayersReady) {
        SetupPlayersReady = setupPlayersReady;
    }

    public StringProperty getGamemode() {
        return gamemode;
    }

    public void setJogador1Nome(String jogador1Nome) {
        Jogador1Nome = jogador1Nome;
    }

    public void setJogador2Nome(String jogador2Nome) {
        Jogador2Nome = jogador2Nome;
    }

    public void setGamemode(String gamemode) {
        this.gamemode.set(gamemode);
    }
}
