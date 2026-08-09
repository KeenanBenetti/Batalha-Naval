package com.batalhaNaval.UI;

import com.batalhaNaval.Model.*;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static com.batalhaNaval.Controller.GameController.*;
import static com.batalhaNaval.Controller.GameController.adicionarBarco;
import static com.batalhaNaval.Controller.GameController.checkarEspaçoParaBarco;


public class Tabuleiro {

    public static StringProperty Mensagem = new SimpleStringProperty();

    public static void MensagemTela(String texto) {
        Mensagem.set(texto);
    }

//    public static void atualizarInterface(int [][] tabuleiro, Button btn){
//        //atualiza o local atirado
//        int [] posicao = (int[]) btn.getUserData();
//        int L = posicao[0];
//        int C = posicao[1];
//        if (tabuleiro[L][C] == 0 || tabuleiro[L][C] == 2){
//            btn.setStyle("-fx-background-color: lightblue;");
//        } else if (tabuleiro[L][C] == 1) {
//            btn.setStyle("-fx-background-color: lightgray;");
//        } else if (tabuleiro[L][C] == 3){
//            btn.setStyle("-fx-background-color: #ff5d5d");
//        }
//
//    }

    public static void ReiniciarJogo(){
        //futuramente irá reiniciar o jogo inteiro
    }

//    public static void TelaVencedor(String texto, Stage primaryStage) {
//        Label aviso = new Label(texto);
//        aviso.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
//        Button reiniciar = new Button("Reiniciar");
//        reiniciar.setOnAction(e -> {
//            primaryStage.close();
//            ReiniciarJogo();
//        });
//        Button fechar = new Button("Fechar Jogo");
//        fechar.setOnAction(e -> System.exit(0));
//        HBox botoes = new HBox(20, reiniciar, fechar);
//        VBox root = new VBox(20, aviso, botoes);
//        Scene cena = new Scene(root, 400, 200);
//        primaryStage.setScene(cena);
//    }

    public static Button[][] criarTabuleiro(GameState gameState, Player jogador){
        Button[][] tabuleiro = new Button[10][10];
        for (int i = 0; i < 10; i++){
            for (int j = 0; j < 10; j++){
                Button btn = new Button();
                btn.setPrefSize(40, 40);
                int L = i;
                int C = j;

                btn.setUserData(new Celula("Agua", L, C));


                tabuleiro[i][j] = btn;

                btn.setOnAction(e ->{
                    if (!gameState.getVezDe().get().equals(jogador.getNome())){
                        MensagemTela("Não é sua vez, aguarde!");
                        return;
                    }
                    if (gameState.getGameStatus().get().equals("Setup")){
                        if (jogador.getBarcoSelecionado().usado){
                            MensagemTela("Esse barco já foi usado, escolha outro!");
                        } else {
                            if (checkarEspaçoParaBarco(L, C, jogador)){
                                adicionarBarco(L, C, jogador, gameState);
                                TrocarPlayer(gameState);
                            } else {
                                MensagemTela("Tente novamente em outro lugar!");
                            }
                        }
                    }
                });
            }
        }

        return tabuleiro;
    }

    public static Button[][] criarTabuleiroOponente(GameState gameState, Player jogador, Player oponente){
        Button[][] tabuleiro = new Button[10][10];
        for (int i = 0; i < 10; i++){
            for (int j = 0; j < 10; j++){
                Button btn = new Button();
                btn.setPrefSize(40, 40);
                int L = i;
                int C = j;

                btn.setUserData(new Celula("Agua", L, C));


                tabuleiro[i][j] = btn;

                btn.setOnAction(e ->{
                    if (!gameState.getVezDe().get().equals(jogador.getNome())){
                        MensagemTela("Não é sua vez, aguarde!");
                        return;
                    }
                    if (gameState.getGameStatus().get().equals("Ready")) {
                        if(checkarTabelaParaAtirar(L, C, jogador)){
                            String resultado = AtirarNoOponente(L, C, jogador, oponente);
                            if (resultado.equals("Errou")){
                                MensagemTela(resultado);
                                TrocarPlayer(gameState);
                                return;
                            }
                            MensagemTela(resultado);
                        } else{
                            MensagemTela("Você já atirou aqui!");
                        }
                    }
                });
            }
        }
        return tabuleiro;
    }

    public static GridPane criarGridPane(Player jogador, String QualTabela){
        Button[][] botoes;
        if (QualTabela.equals("Principal")){
            botoes = jogador.getTabuleiro();
        } else{
            botoes = jogador.getTabuleiroOponente();
        }
        GridPane grid = new GridPane();
        for (int i = 0; i < 10; i++){
            for (int j = 0; j < 10; j++){
                Button btn = botoes[i][j];
                grid.add(btn, j, i);
            }
        }
        return grid;
    }

//    public static void atualizarTabuleiroCompleto(int[][] tabuleiro, Button[][] botoes, boolean MostrarBarcos) {
//        for (int i = 0; i < 10; i++) {
//            for (int j = 0; j < 10; j++) {
//
//                Button btn = botoes[i][j];
//
//                if (tabuleiro[i][j] == 0) {
//                    btn.setStyle("-fx-background-color: lightblue;"); // água
//                }
//                else if (tabuleiro[i][j] == 2) {
//                    if (MostrarBarcos) {
//                        btn.setStyle("-fx-background-color: green;"); // barco visível (debug)
//                    } else {
//                        btn.setStyle("-fx-background-color: lightblue;");
//                    }
//                }
//                else if (tabuleiro[i][j] == 1) {
//                    btn.setStyle("-fx-background-color: lightgray;");
//                }
//                else if (tabuleiro[i][j] == 3) {
//                    btn.setStyle("-fx-background-color: red;");
//                }
//            }
//        }
//    }

    public static void selecionarBarco(Player jogador, int index ) {
        Barco[] barcos = jogador.getBarcosDoPlayer();
        Barco barco = barcos[index];

        if (barco.usado) {
            MensagemTela("Esse barco já foi usado!");
            return;
        }

        jogador.setBarcoSelecionado(barco);
        MensagemTela("Selecionou: " + barco.nome);
    }

    public static HBox criarBarquinhos(Player jogador){
        HBox barquinhos = new HBox(20);

        Barco[] barcosPlayer = jogador.getBarcosDoPlayer();

        for (int i = 0; i < barcosPlayer.length; i++) {
            GridPane barquinho = new GridPane();
            for (int j = 0; j < barcosPlayer[i].tamanho; j++) {
                Button btn = new Button();
                int FinalI = i;
                btn.setOnAction(e -> selecionarBarco(jogador, FinalI));
                barquinho.add(btn, i, j);
            }
            barquinhos.getChildren().add(barquinho);
        }

        Label lborientaçao = new Label();
        lborientaçao.textProperty().bind(Bindings.concat("Orientação Atual: ", jogador.getOrientacaoDoPosicionamento()));
        Button btn = new Button("Mudar Orientação");
        btn.setOnAction(e-> trocarOrientacao(jogador));
        VBox vbox = new VBox(20, btn, lborientaçao);
        barquinhos.getChildren().add(vbox);
        return barquinhos;
    }

}
