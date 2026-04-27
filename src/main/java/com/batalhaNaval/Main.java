package com.batalhaNaval;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {

    IntegerProperty jogadorAtual= new SimpleIntegerProperty(1);
    private Stage primaryStage;
    Barco BarcoSelecionado = null;
    String GameStatus = "Setup";
    StringProperty Orientaçao = new SimpleStringProperty("Horizontal");
    boolean MostrarBarcos = true;
    Button[][] botoesP1 = new Button[10][10];
    Button[][] botoesP2 = new Button[10][10];
    boolean Player1Done = false;
    boolean Player2Done = false;
    Barco[] barcosPlayer1 = new Barco[5];
    Barco[] barcosPlayer2 = new Barco[5];
    StringProperty Mensagem = new SimpleStringProperty();

    public boolean adicionarBarco(int[][] Tabuleiro, int L, int C, StringProperty Orientaçao, Barco barco) {
        //Permite adicionar um barco ao tabuleiro se couber
        if (barco.usado) {
            MensagemTela("Esse Barco Já Foi Usado!");
            return false;
        }
        int TamanhoBarco = barco.tamanho;
        boolean NaoCabe = false;

        if (Orientaçao.get().equals("Horizontal")) {
            if (C + TamanhoBarco > Tabuleiro[0].length) {
                MensagemTela("Esse barco não cabe aqui!");
                return false;
            }

            for (int i = 0; i < TamanhoBarco; i++) {
                int ColunaTeste = C + i;
                if (Tabuleiro[L][ColunaTeste] != 0) {
                    NaoCabe = true;
                    break;
                }
            }

            if (NaoCabe) {
                MensagemTela("O barco selecionado não cabe aqui");
                return false;
            }

            for (int i = 0; i < TamanhoBarco; i++) {
                int Coluna = C + i;
                Tabuleiro[L][Coluna] = 2;
            }

            barco.usado = true;
            return true;
        } else if (Orientaçao.get().equals("Vertical")) {
            if (L + TamanhoBarco > Tabuleiro.length) {
                MensagemTela("Esse barco não cabe aqui!");
                return false;
            }

            for (int i = 0; i < TamanhoBarco; i++) {
                int LinhaTeste = L + i;
                if (Tabuleiro[LinhaTeste][C] != 0) {
                    NaoCabe = true;
                    break;
                }
            }

            if (NaoCabe) {
                MensagemTela("O barco selecionado não cabe aqui!");
                return false;
            }

            for (int i = 0; i < TamanhoBarco; i++) {
                int Linha = L + i;
                Tabuleiro[Linha][C] = 2;
            }
            barco.usado = true;
            return true;
        }
        return false;
    }

    public void atirar(int[][] Tabuleiro, int L, int C) {
        if(!GameStatus.equals("Setup")){
            //Permite verificar se acertou ou não um barco
            if (Tabuleiro[L][C] == 2) {
                Tabuleiro[L][C] = 3;
                MensagemTela("Acertou");
            } else if (Tabuleiro[L][C] == 0) {
                Tabuleiro[L][C] = 1;
                MensagemTela( "Errou");
                MensagemTela( "Errou");
            }
        }
    }

    public int JaFoiUsado(int[][] Tabuleiro, int L, int C) {
        //verifica se o local já foi acertado antes
        if (Tabuleiro[L][C] == 1 || Tabuleiro[L][C] == 3) {
            MensagemTela("Já Atirou Aí!");
            return 1;
        } else {
            return 0;
        }
    }

    public void CriarBarcos(Barco[] barcos) {
        barcos[0] = new Barco("Porta-aviões", 5);
        barcos[1] = new Barco("Encouraçado", 4);
        barcos[2] = new Barco("Cruzador", 3);
        barcos[3] = new Barco("Submarino", 3);
        barcos[4] = new Barco("Destroyer", 2);
    }

    public void IniciarTabuleiro(int[][] Tabuleiro) {
        for (int i = 0; i < Tabuleiro.length; i++) {
            for (int ii = 0; ii < Tabuleiro[0].length; ii++) {
                Tabuleiro[i][ii] = 0;
            }
        }
    }

    public static void atualizarInterface(int [][] tabuleiro, Button btn){
        //atualiza o local atirado
        int [] posicao = (int[]) btn.getUserData();
        int L = posicao[0];
        int C = posicao[1];
        if (tabuleiro[L][C] == 0 || tabuleiro[L][C] == 2){
            btn.setStyle("-fx-background-color: lightblue;");
        } else if (tabuleiro[L][C] == 1) {
            btn.setStyle("-fx-background-color: lightgray;");
        } else if (tabuleiro[L][C] == 3){
            btn.setStyle("-fx-background-color: #ff5d5d");
        }

    }

    public static void ReiniciarJogo(){
        //futuramente irá reiniciar o jogo inteiro
    }

    public void TelaVencedor(String texto) {
        Label aviso = new Label(texto);
        aviso.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        Button reiniciar = new Button("Reiniciar");
        reiniciar.setOnAction(e -> {
            primaryStage.close();
            ReiniciarJogo();
        });
        Button fechar = new Button("Fechar Jogo");
        fechar.setOnAction(e -> System.exit(0));
        HBox botoes = new HBox(20, reiniciar, fechar);
        VBox root = new VBox(20, aviso, botoes);
        Scene cena = new Scene(root, 400, 200);
        primaryStage.setScene(cena);
    }

    public GridPane criarTabuleiro(int[][] tabuleiro, boolean ehPlayer1) {
        GridPane grid = new GridPane();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Button btn = new Button();
                btn.setPrefSize(40, 40);
                int L = i;
                int C = j;
                btn.setUserData(new int[]{L, C});

                if (ehPlayer1) {
                    botoesP1[i][j] = btn;
                } else {
                    botoesP2[i][j] = btn;
                }
                btn.setOnAction(e -> {
                    if ((jogadorAtual.get() == 1 && !ehPlayer1) && GameStatus.equals("Ready")) {
                        atirar(tabuleiro, L, C);
                        jogadorAtual.set(2);
                        atualizarTabuleiroCompleto(tabuleiro, ehPlayer1 ? botoesP1 : botoesP2);
                        if (checkarTabelaForWin(tabuleiro)) {
                            TelaVencedor("Player 1 Venceu");
                        }
                    }
                    else if ((jogadorAtual.get() == 2 && ehPlayer1) && GameStatus.equals("Ready")) {
                        atirar(tabuleiro, L, C);
                        jogadorAtual.set(1);
                        atualizarTabuleiroCompleto(tabuleiro, ehPlayer1 ? botoesP1 : botoesP2);
                        if (checkarTabelaForWin(tabuleiro)) {
                            TelaVencedor("Player 2 Venceu");
                        }
                    }
                    else if ((jogadorAtual.get() == 1 && !ehPlayer1) && GameStatus.equals("Setup")) {
                        if (BarcoSelecionado == null) {
                            MensagemTela("Selecione um barco primeiro!");
                            return;
                        }
                        if (!BarcoSelecionado.usado) {
                            if (adicionarBarco(tabuleiro, L, C, Orientaçao, BarcoSelecionado)) {
                                checkarStatusPlayer();
                                jogadorAtual.set(2);
                                atualizarTabuleiroCompleto(tabuleiro, ehPlayer1 ? botoesP1 : botoesP2);
                            } else {
                                MensagemTela("Tente novamente em outro lugar");
                            }
                        }
                    }
                    else if ((jogadorAtual.get() == 2 && ehPlayer1) && GameStatus.equals("Setup")) {
                        if (BarcoSelecionado == null) {
                            MensagemTela("Selecione um barco primeiro!");
                            return;
                        }
                        if (!BarcoSelecionado.usado) {
                            if (adicionarBarco(tabuleiro, L, C, Orientaçao, BarcoSelecionado)) {
                                checkarStatusPlayer();
                                jogadorAtual.set(1);
                                atualizarTabuleiroCompleto(tabuleiro, ehPlayer1 ? botoesP1 : botoesP2);
                            } else {
                                MensagemTela("Tente novamente em outro lugar");
                            }
                        }
                    }
                });
                grid.add(btn, j, i);
            }
        }
        atualizarTabuleiroCompleto(tabuleiro, ehPlayer1 ? botoesP1 : botoesP2);
        return grid;
    }

    public void checkarStatusPlayer(){
        if (jogadorAtual.get() == 1){
            for (int i = 0; i < barcosPlayer1.length; i++) {
                if (!barcosPlayer1[i].usado){
                    Player1Done = false;
                    return;
                }
            }
            Player1Done = true;
            checkarStatusPlayers();
        }
        if (jogadorAtual.get() == 2){
            for (int i = 0; i < barcosPlayer2.length; i++) {
                if (!barcosPlayer2[i].usado){
                    Player2Done = false;
                    return;
                }
            }
            Player2Done = true;
            checkarStatusPlayers();
        }
    }

    public void checkarStatusPlayers(){
        if(Player1Done && Player2Done){
            MostrarBarcos=false;
            GameStatus = "Ready";
        }
    }

    public void atualizarTabuleiroCompleto(int[][] tabuleiro, Button[][] botoes) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {

                Button btn = botoes[i][j];

                if (tabuleiro[i][j] == 0) {
                    btn.setStyle("-fx-background-color: lightblue;"); // água
                }
                else if (tabuleiro[i][j] == 2) {
                    if (MostrarBarcos) {
                        btn.setStyle("-fx-background-color: green;"); // barco visível (debug)
                    } else {
                        btn.setStyle("-fx-background-color: lightblue;");
                    }
                }
                else if (tabuleiro[i][j] == 1) {
                    btn.setStyle("-fx-background-color: lightgray;");
                }
                else if (tabuleiro[i][j] == 3) {
                    btn.setStyle("-fx-background-color: red;");
                }
            }
        }
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

    public void MensagemTela(String texto) {
        Mensagem.set(texto);
    }

    public void selecionarBarco(Barco barco) {
        if (barco.usado) {
            MensagemTela("Esse barco já foi usado!");
            return;
        }
        BarcoSelecionado = barco;
        MensagemTela("Selecionou: " + barco.nome);
    }

    public HBox criarBarquinhos(Barco[] barcosPlayer){
        HBox barquinhos = new HBox(20);
        for (int i = 0; i < barcosPlayer.length; i++) {
            GridPane barquinho = new GridPane();
            for (int j = 0; j < barcosPlayer[i].tamanho; j++) {
                Button btn = new Button();
                int finalI = i;//se o inteliJ disse, Não entendi mas ok
                btn.setOnAction(e -> selecionarBarco(barcosPlayer[finalI]));
                barquinho.add(btn, i, j);
            }
            barquinhos.getChildren().add(barquinho);
        }
        Label lborientaçao = new Label();
        lborientaçao.textProperty().bind(Bindings.concat("Orientação Atual: ", Orientaçao));
        Button btn = new Button("Mudar Orientação");
        btn.setOnAction(e->trocarOrientaçao());
        VBox vbox = new VBox(20, btn, lborientaçao);
        barquinhos.getChildren().add(vbox);
        return barquinhos;
    }

    public void trocarOrientaçao(){
        if (Orientaçao.get().equals("Horizontal")){
            Orientaçao.set("Vertical");

        } else if (Orientaçao.get().equals("Vertical")) {
            Orientaçao.set("Horizontal");
        }
    }

    class Valores {
        public static final int AGUA = 0;
        public static final int USADO = 1;
        public static final int BARCO = 2;
        public static final int ACERTO = 3;
    }

    class Barco {
        String nome;
        int tamanho;
        boolean usado;

        Barco(String nome, int tamanho) {
            this.nome = nome;
            this.tamanho = tamanho;
            this.usado = false;
        }
    }
    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        int[][] tabuleiroPlayer1 = new int[10][10];
        int[][] tabuleiroPlayer2 = new int[10][10];

        CriarBarcos(barcosPlayer1);
        CriarBarcos(barcosPlayer2);
        IniciarTabuleiro(tabuleiroPlayer1);
        IniciarTabuleiro(tabuleiroPlayer2);

        //layout inicial
        // duas caixas verticais uma para guardar os barcos no inicio do jogo e outra para a tabela


        GridPane gridP1 = criarTabuleiro(tabuleiroPlayer1, true);
        GridPane gridP2 = criarTabuleiro(tabuleiroPlayer2, false);

        Label status = new Label();
        status.textProperty().bind(jogadorAtual.asString("Vez do jogador %d"));
        VBox statusBox = new VBox(20, status);
        statusBox.setStyle("-fx-alignment: center");
        HBox barquinhosPlayer1 = criarBarquinhos(barcosPlayer1);
        HBox barquinhosPlayer2 = criarBarquinhos(barcosPlayer2);
        HBox grids = new HBox(20, gridP1, gridP2);
        VBox player2 = new VBox(gridP2, barquinhosPlayer2);
        VBox player1 = new VBox(gridP1, barquinhosPlayer1);
        HBox colunas = new HBox(40, player1, player2);
        Label mensagem = new Label();
        mensagem.textProperty().bind(Mensagem);
        VBox Caixamensagem = new VBox(20, mensagem);
        Caixamensagem.setStyle("-fx-alignment: center");
        VBox root = new VBox(20, statusBox, colunas, Caixamensagem);
        Scene tela = new Scene(root, 800, 600);
        stage.setScene(tela);
        stage.setTitle("Batalha Naval");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
        //Batalha Naval:
        //Objetivo Principal:Criar um programa de batalha naval
        //Objetivos secundarios
        // conexão multiplayer na rede local via sockets
        // Tela feita usando JavaFX
        // Menu para escolha de multiplayer ou singleplayer
        // tela de configurações do jogo
        // Implementar drag e click para colocar os barcos no tabuleiro

}