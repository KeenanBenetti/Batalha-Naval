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
        Celula celulaTiro = (Celula) Tabuleiro[L][C].getUserData();
        if (celulaTiro.status.get().equals("Barco")){
            acertou = true;
            if (celulaTiro.orientacaoBarco.equals("Vertical")){
                int posicaoInicial = L + 1 - celulaTiro.pedacoN;
                int totalAcertos = 0;
                for (int i = posicaoInicial; i < posicaoInicial+celulaTiro.barco.tamanho; i++){
                    Celula celula = (Celula) Tabuleiro[i][C].getUserData();
                    if (celula.status.get().equals("Acerto")){
                        totalAcertos +=1;
                    }
                }
                if (totalAcertos +1 == celulaTiro.barco.tamanho){
                    afundou = true;
                }
            } else{
                int posicaoInicial = C + 1 - celulaTiro.pedacoN;
                int totalAcertos = 0;
                for (int i = posicaoInicial; i < posicaoInicial + celulaTiro.barco.tamanho; i++){
                    Celula celula = (Celula) Tabuleiro[L][i].getUserData();
                    if (celula.status.get().equals("Acerto")){
                        totalAcertos +=1;
                    }
                }
                if (totalAcertos +1 == celulaTiro.barco.tamanho){
                    afundou = true;
                }
            }
        }
        return new ResultadoTiro(acertou, afundou);
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
