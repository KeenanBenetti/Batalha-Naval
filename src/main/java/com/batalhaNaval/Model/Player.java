package com.batalhaNaval.Model;

import com.batalhaNaval.Controller.GameController;
import javafx.scene.control.Button;

import static com.batalhaNaval.UI.Tabuleiro.criarTabuleiro;

public class Player {
    String Nome;
    Button[][] Tabuleiro;
    String StatusDoPlayer;
    String OrientacaoDoPosicionamento;
    Barco[] BarcosDoPlayer;
    Barco BarcoSelecionado;

    public Player(String nome, GameState gameState) {
        this.Nome = nome;
        this.StatusDoPlayer = "Setup";
        this.OrientacaoDoPosicionamento = "Vertical";
        this.BarcosDoPlayer = GameController.CriarBarcos();
        this.BarcoSelecionado = BarcosDoPlayer[0];
        this.Tabuleiro = criarTabuleiro(gameState, this);
    }

    public Barco getBarcoSelecionado() {
        return BarcoSelecionado;
    }

    public void setBarcoSelecionado(Barco barcoSelecionado) {
        BarcoSelecionado = barcoSelecionado;
    }

    public String getOrientacaoDoPosicionamento(){
        return OrientacaoDoPosicionamento;
    }

    public void setOrientaçãoDoPosicionamento(String orientaçãoDoPosicionamento) {
        OrientacaoDoPosicionamento = orientaçãoDoPosicionamento;
    }

    public Button[][] getTabuleiro() {
        return Tabuleiro;
    }

    public Barco[] getBarcosDoPlayer() {
        return BarcosDoPlayer;
    }

    public String getNome() {
        return Nome;
    }
}
