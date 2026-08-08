package com.batalhaNaval;

import com.batalhaNaval.Controller.GameController;
import com.batalhaNaval.Model.Barco;
import com.batalhaNaval.Model.GameState;
import com.batalhaNaval.Model.Player;
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

import static com.batalhaNaval.Controller.GameController.CriarBarcos;
import static com.batalhaNaval.Controller.GameController.adicionarBarco;
import static com.batalhaNaval.Controller.GameController.checkarStatusPlayer;
import static com.batalhaNaval.Controller.GameController.checkarTabelaForWin;
import static com.batalhaNaval.Controller.GameController.trocarOrientaçao;
import static com.batalhaNaval.UI.Tabuleiro.*;


public class Main extends Application {

    IntegerProperty jogadorAtual= new SimpleIntegerProperty(1);
    private Stage primaryStage;


    public int JaFoiUsado(int[][] Tabuleiro, int L, int C) {
        //verifica se o local já foi acertado antes
        if (Tabuleiro[L][C] == 1 || Tabuleiro[L][C] == 3) {
            MensagemTela("Já Atirou Aí!");
            return 1;
        } else {
            return 0;
        }
    }

    GameState gameState = new GameState("Jogador 1", "Setup");

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;

        Player jogador1 = new Player("Jogador 1", gameState);
        Player jogador2 = new Player("Jogador 2", gameState);

        //layout inicial
        // duas caixas verticais uma para guardar os barcos no inicio do jogo e outra para a tabela

        GridPane gridP1 = criarGridPane(jogador1);
        GridPane gridP2 = criarGridPane(jogador2);

        Label status = new Label();
        status.textProperty().bind(jogadorAtual.asString("Vez do jogador %d"));
        VBox statusBox = new VBox(20, status);
        statusBox.setStyle("-fx-alignment: center");
        HBox barquinhosPlayer1 = criarBarquinhos(jogador1, gameState);
        HBox barquinhosPlayer2 = criarBarquinhos(jogador2, gameState);
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