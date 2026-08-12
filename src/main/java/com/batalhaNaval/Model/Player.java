package com.batalhaNaval.Model;

import com.batalhaNaval.Controller.GameController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;

import static com.batalhaNaval.UI.Tabuleiro.criarTabuleiro;

public class Player {
    String Nome;
    Button[][] Tabuleiro;
    Button[][] TabuleiroOponente;
    String StatusDoPlayer;
    StringProperty OrientacaoDoPosicionamento = new SimpleStringProperty();
    Barco[] BarcosDoPlayer;
    Barco BarcoSelecionado;

    public Player(String nome, GameState gameState) {
        this.Nome = nome;
        this.StatusDoPlayer = "Setup";
        this.OrientacaoDoPosicionamento.set("Vertical");
        this.BarcosDoPlayer = GameController.CriarBarcos();
        this.BarcoSelecionado = BarcosDoPlayer[0];
        this.Tabuleiro = criarTabuleiro(gameState, this);
    }

    public ResultadoTiro ReceberTiro(int L, int C){
        boolean acertou = false;
        boolean afundou = false;
        boolean venceu = false;
        Celula celulaTiro = (Celula) Tabuleiro[L][C].getUserData();
        if (celulaTiro.status.get().equals("Barco")){
            acertou = true;
            celulaTiro.status.set("Explosao");
            if (celulaTiro.barco.barcoAfundado(celulaTiro.barco)){
                afundou = true;
                if (CheckarVitoriaOponente()){
                    venceu = true;
                } else{
                    venceu = false;
                }
            }
        } else if (celulaTiro.status.get().equals("Agua")){
            celulaTiro.status.set("Errou");
        }
        return new ResultadoTiro(acertou, afundou, venceu);
    }

    public boolean CheckarVitoriaOponente(){
        Barco[] barcos = getBarcosDoPlayer();
        int afundados = 0;
        for (int i = 0; i < barcos.length; i++) {
            if (barcos[i].afundado){
                afundados += 1;
            }
        }
        if (afundados == barcos.length){
            return true;
        }
        return false;
    }

    public Barco getBarcoSelecionado() {
        return BarcoSelecionado;
    }

    public void setBarcoSelecionado(Barco barcoSelecionado) {
        BarcoSelecionado = barcoSelecionado;
    }

    public StringProperty getOrientacaoDoPosicionamento(){
        return OrientacaoDoPosicionamento;
    }

    public void setOrientaçãoDoPosicionamento(String orientaçãoDoPosicionamento) {
        OrientacaoDoPosicionamento.set(orientaçãoDoPosicionamento);
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

    public void setTabuleiroOponente(Button[][] tabuleiroOponente) {
        TabuleiroOponente = tabuleiroOponente;
    }

    public Button[][] getTabuleiroOponente() {
        return TabuleiroOponente;
    }
}
