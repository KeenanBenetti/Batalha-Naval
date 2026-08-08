package com.batalhaNaval.Controller;

import com.batalhaNaval.Model.GameState;
import javafx.beans.property.StringProperty;
import com.batalhaNaval.Model.Barco;
import com.batalhaNaval.Model.Player;
import com.batalhaNaval.Model.Celula;
import javafx.scene.control.Button;

import static com.batalhaNaval.UI.Tabuleiro.MensagemTela;

public class GameController {

    public static void adicionarBarco(int L, int C, Player jogador){
        Button[][] botoes = jogador.getTabuleiro();
        Barco barco = jogador.getBarcoSelecionado();
        String orientaçao = jogador.getOrientaçãoDoPosicionamento();
        if (orientaçao.equals("Vertical")) {
            for (int i = C; i <= barco.tamanho; i++) {
                Celula celula = (Celula) botoes[L][i].getUserData();
                celula.status = "barco";
            }
        } else if (orientaçao.equals("Horizontal")){
            for (int i = L; i <= barco.tamanho; i++){
                Celula celula = (Celula) botoes[i][C].getUserData();
                celula.status = "barco";
            }
        }
    }

    public static boolean atirar(int L, int C, Player jogador) {
        Button[][] botoes = jogador.getTabuleiro();
        Button btn = botoes[L][C];
        Celula celula = (Celula) btn.getUserData();

        if (celula.status.equals("agua")){
            return false;
        } else {
            return true;
        }
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
        Button btn = tabuleiro[L][C];
        Celula celula = (Celula) tabuleiro[L][C].getUserData();

        if (!celula.status.equals("Missed")){
            return false;
        }
        return true;
    }

    public static boolean checkarTabelaForWin(int[][] tabela) {
        for (int i = 0; i < tabela.length; i++) {
            for (int j = 0; j < tabela[0].length; j++) {
                if (tabela[i][j] == 2) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void trocarOrientaçao(Player jogador){
        String Orientaçao = jogador.getOrientaçãoDoPosicionamento();
        if (Orientaçao.equals("Horizontal")){
            jogador.setOrientaçãoDoPosicionamento("Vertical");

        } else if (Orientaçao.equals("Vertical")) {
            jogador.setOrientaçãoDoPosicionamento("Horizontal");
        }
    }

    public static boolean checkarEspaçoParaBarco(int L, int C, Player jogador){
        int tamanho = jogador.getBarcoSelecionado().tamanho;
        String Orientação = jogador.getOrientaçãoDoPosicionamento();
        Button[][] tabuleiro = jogador.getTabuleiro();

        if (Orientação.equals("Vertical") && L + tamanho <=10){
            for (int i = C; i<= tamanho; i++){
                Celula celula = (Celula) tabuleiro[L][i].getUserData();

                if (!celula.status.equals("Agua") ){
                    return false;
                }
            }
            return true;
        } else if (Orientação.equals("Horizontal") && L + tamanho <=10) {
            for (int i = L; i <= tamanho; i++){
                Celula celula = (Celula) tabuleiro[i][C].getUserData();

                if (!celula.status.equals("Agua") ){
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public static void TrocarPlayer(GameState gameState){
        String jogadorAtual = gameState.getVezDe();
        if (jogadorAtual.equals("Player 1")){
            gameState.setVezDe("Jogador 2");
        } else {
            gameState.setVezDe("Jogador 1");
        }
    }
}
