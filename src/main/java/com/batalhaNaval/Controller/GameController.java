package com.batalhaNaval.Controller;

import com.batalhaNaval.Model.*;
import com.batalhaNaval.UI.Tabuleiro;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import static com.batalhaNaval.UI.Tabuleiro.TelaVencedor;
import static com.batalhaNaval.UI.Tabuleiro.mudarCorCelula;

public class GameController {

    public static void adicionarBarco(int L, int C, Player jogador, GameState gameState){
        Button[][] botoes = jogador.getTabuleiro();
        Barco barco = jogador.getBarcoSelecionado();
        StringProperty orientaçao = jogador.getOrientacaoDoPosicionamento();
        int pedacoN = 1;
        if (orientaçao.get().equals("Vertical")) {
            for (int i = L; i < L + barco.tamanho; i++) {
                Celula celula = (Celula) botoes[i][C].getUserData();
                celula.status.set("Barco");
                celula.setBarco(barco, orientaçao.get(), pedacoN);
                barco.usado = true;
                pedacoN++;
            }
        } else if (orientaçao.get().equals("Horizontal")){
            for (int i = C; i < C + barco.tamanho; i++){
                Celula celula = (Celula) botoes[L][i].getUserData();
                celula.status.set("Barco");
                celula.setBarco(barco, orientaçao.get(), pedacoN);
                barco.usado = true;
                pedacoN++;
            }
        }
        if (checkarStatusPlayer(jogador)){
            gameState.setSetupPlayersReady(gameState.getSetupPlayersReady()+1);
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

    public static boolean checkarStatusPlayer(Player jogador){
        Barco[] barcos = jogador.getBarcosDoPlayer();
        for (Barco barco : barcos) {
            if (!barco.usado) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkarTabelaParaAtirar(int L, int C, Player jogador){
        Button[][] tabuleiro = jogador.getTabuleiroOponente();
        Celula celula = (Celula) tabuleiro[L][C].getUserData();
        return celula.status.get().equals("Agua");
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

                if (!celula.status.get().equals("Agua") ){
                    return false;
                }
            }
            return true;
        } else if (Orientação.get().equals("Horizontal") && C + tamanho <=10) {
            for (int i = C; i < C + tamanho; i++){
                Celula celula = (Celula) tabuleiro[L][i].getUserData();

                if (!celula.status.get().equals("Agua") ){
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

        if (gameState.getSetupPlayersReady() == 2){
            gameState.setGameStatus("Ready");
        }

        if (JogadorAtual.equals(Jogador1)){
            gameState.setVezDe(Jogador2);
        } else {
            gameState.setVezDe(Jogador1);
        }
    }

    public static String AtirarNoOponente(int L, int C, Player jogador, Player oponente, GameState gameState){
        ResultadoTiro resultadoTiro = oponente.ReceberTiro(L, C);
        Button[][] tabuleiro = jogador.getTabuleiroOponente();
        Celula celula = (Celula) tabuleiro[L][C].getUserData();
        if (resultadoTiro.Venceu){
            gameState.setVencedor(jogador.getNome());
        }
        if (resultadoTiro.Acertou){
            celula.status.set("Acerto");
            if(resultadoTiro.Afundou){
                return "Navio Afundado";
            }

            return "Acertou!";
        }

        celula.status.set("Errou");

        return "Errou";
    }

    public static void AddListenerCores(Player jogador, String qualTabela){
        Button[][] tabela;

        if (qualTabela.equals("Principal")){
            tabela = jogador.getTabuleiro();
        } else{
            tabela = jogador.getTabuleiroOponente();
        }
        for (int i = 0; i < tabela.length; i++ ){
            for (int j = 0; j < tabela[i].length; j++){
                Button btn = tabela[i][j];

                Celula celula = (Celula) btn.getUserData();

                celula.status.addListener((observable, oldValue, newValue) -> {
                    mudarCorCelula(btn, qualTabela);
                });

                celula.hover.addListener(((observable, oldValue, newValue) -> {
                    switch (celula.hover.get()) {
                        case "Hover" -> btn.setStyle("-fx-background-color: gray;");
                        case "" -> {
                            mudarCorCelula(btn, qualTabela);
                        }
                    }
                }));
            }
        }
    }

    public static void AdicionarHoverBarco(int L, int C, Player jogador){
        Button[][] tabuleiro = jogador.getTabuleiro();

        if (jogador.getOrientacaoDoPosicionamento().get().equals("Vertical")){
            for (int i = L; i < L + jogador.getBarcoSelecionado().tamanho; i++) {
                Celula celula = (Celula) tabuleiro[i][C].getUserData();
                celula.hover.set("Hover");
            }
        } else {
            for (int i = C; i < C + jogador.getBarcoSelecionado().tamanho; i++) {
                Celula celula = (Celula) tabuleiro[L][i].getUserData();
                celula.hover.set("Hover");
            }
        }
    }

    public static void RemoverHoverBarco(int L, int C, Player jogador){
        Button[][] tabuleiro = jogador.getTabuleiro();

        if (jogador.getOrientacaoDoPosicionamento().get().equals("Vertical")){
            for (int i = L; i < L + jogador.getBarcoSelecionado().tamanho; i++) {
                Celula celula = (Celula) tabuleiro[i][C].getUserData();
                celula.hover.set("");
            }
        } else {
            for (int i = C; i < C + jogador.getBarcoSelecionado().tamanho; i++) {
                Celula celula = (Celula) tabuleiro[L][i].getUserData();
                celula.hover.set("");
            }
        }
    }

    public static void ListenerVencedor(GameState gameState, Stage telaJogador1, Stage telaJogador2){
        StringProperty vencedor = gameState.getVencedor();
        String player1Nome = gameState.getJogador1Nome();
        String player2Nome = gameState.getJogador2Nome();

        vencedor.addListener(observable -> {
            String v = vencedor.get();
            if (v.equals(player1Nome)||v.equals(player2Nome)){
                TelaVencedor(v, telaJogador1, telaJogador2);
            }
        });
    }
}
