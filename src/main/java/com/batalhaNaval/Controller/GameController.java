package com.batalhaNaval.Controller;

import com.batalhaNaval.Model.GameState;
import com.batalhaNaval.Model.Barco;
import com.batalhaNaval.Model.Player;
import com.batalhaNaval.Model.Celula;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;

public class GameController {

    public static void adicionarBarco(int L, int C, Player jogador){
        Button[][] botoes = jogador.getTabuleiro();
        Barco barco = jogador.getBarcoSelecionado();
        StringProperty orientaçao = jogador.getOrientacaoDoPosicionamento();
        if (orientaçao.get().equals("Vertical")) {
            for (int i = L; i < L + barco.tamanho; i++) {
                Celula celula = (Celula) botoes[i][C].getUserData();
                celula.status = "Barco";
                botoes[i][C].setStyle("-fx-background-color:red;");
                barco.usado = true;
            }
        } else if (orientaçao.get().equals("Horizontal")){
            for (int i = C; i < C + barco.tamanho; i++){
                Celula celula = (Celula) botoes[L][i].getUserData();
                celula.status = "Barco";
                botoes[L][i].setStyle("-fx-background-color:red;");
                barco.usado = true;
            }
        }
    }

    public static boolean atirar(int L, int C, Player jogador, GameState gameState) {
        Button[][] botoes = jogador.getTabuleiro();
        Celula celula = (Celula) botoes[L][C].getUserData();
        if (celula.status.equals("Barco")){
            celula.status = "Acerto";
            checkarTabelaForWin(jogador, gameState);
            return true;
        }
        return false;
    }

    public static Barco[] CriarBarcos() {
        Barco[] barcos = new Barco[5];
        barcos[0] = new Barco("Porta-aviões", 5);
        barcos[1] = new Barco("Encouraçado", 4);
        barcos[2] = new Barco("Cruzador", 3);
        barcos[3] = new Barco("Submarino", 3);
        barcos[4] = new Barco("Destroyer", 2);

        return barcos;
    }

    public static boolean checkarStatusPlayer(Barco[] barcosPlayerAtual){
        for (Barco barco : barcosPlayerAtual) {
            if (!barco.usado) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkarTabelaParaAtirar(int L, int C, Player jogador){
        Button[][] tabuleiro = jogador.getTabuleiro();
        Celula celula = (Celula) tabuleiro[L][C].getUserData();
        if (celula.status.equals("Agua")||celula.status.equals("Barco")){
            return true;
        }
        return false;
    }

    public static void checkarTabelaForWin(Player jogador, GameState gameState) {
        Button[][] tabela = jogador.getTabuleiro();

        for (int i = 0; i < tabela.length; i++) {
            for (int j = 0; j < tabela[0].length; j++) {
                Celula celula = (Celula) tabela[i][j].getUserData();

                if (celula.status.equals("Barco") ) {
                    return;
                }
            }
        }
        gameState.setGameStatus("Finalizado");
        gameState.setVencedor(jogador.getNome());
    }

    public static void trocarOrientacao(Player jogador){
        StringProperty Orientaçao = jogador.getOrientacaoDoPosicionamento();
        if (Orientaçao.get().equals("Horizontal")){
            jogador.setOrientaçãoDoPosicionamento("Vertical");

        } else if (Orientaçao.get().equals("Vertical")) {
            jogador.setOrientaçãoDoPosicionamento("Horizontal");
        }
    }

    public static boolean checkarEspaçoParaBarco(int L, int C, Player jogador){
        int tamanho = jogador.getBarcoSelecionado().tamanho;
        StringProperty Orientação = jogador.getOrientacaoDoPosicionamento();
        Button[][] tabuleiro = jogador.getTabuleiro();

        if (Orientação.get().equals("Vertical") && L + tamanho <=10){
            for (int i = L; i < L + tamanho; i++){
                Celula celula = (Celula) tabuleiro[i][C].getUserData();

                if (!celula.status.equals("Agua") ){
                    return false;
                }
            }
            return true;
        } else if (Orientação.get().equals("Horizontal") && C + tamanho <=10) {
            for (int i = C; i < C + tamanho; i++){
                Celula celula = (Celula) tabuleiro[L][i].getUserData();

                if (!celula.status.equals("Agua") ){
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public static void TrocarPlayer(GameState gameState){
        String JogadorAtual = gameState.getVezDe().get();
        String Jogador1 = gameState.getJogador1Nome();
        String Jogador2 = gameState.getJogador2Nome();

        if (JogadorAtual.equals(Jogador1)){
            gameState.setVezDe(Jogador2);
        } else {
            gameState.setVezDe(Jogador1);
        }
    }
}
