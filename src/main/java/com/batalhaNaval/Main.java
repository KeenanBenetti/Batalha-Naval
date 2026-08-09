package com.batalhaNaval;

import com.batalhaNaval.Model.GameState;
import com.batalhaNaval.Model.Player;
import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static com.batalhaNaval.Controller.GameController.AddListenerCores;
import static com.batalhaNaval.UI.Tabuleiro.*;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        Stage secundaryStage = new Stage();

        GameState gameState = new GameState("Jogador 1",  "Jogador 1", "Jogador 2", "Setup");

        Player jogador1 = new Player("Jogador 1", gameState);
        AddListenerCores(jogador1, "Principal");
        Player jogador2 = new Player("Jogador 2", gameState);
        AddListenerCores(jogador2, "Principal");
        jogador1.setTabuleiroOponente(criarTabuleiroOponente(gameState, jogador1, jogador2));
        AddListenerCores(jogador1, "Oponente");
        jogador2.setTabuleiroOponente(criarTabuleiroOponente(gameState, jogador2, jogador1));
        AddListenerCores(jogador2, "Oponente");

        //layout inicial
        // duas caixas verticais uma para guardar os barcos no inicio do jogo e outra para a tabela

        GridPane gridP1 = criarGridPane(jogador1, "Principal");
        GridPane gridP2 = criarGridPane(jogador2, "Principal");
        GridPane gridP1Oponente = criarGridPane(jogador1, "Oponente");
        GridPane gridP2Oponente = criarGridPane(jogador2, "Oponente");

        Label status1 = new Label();
        Label status2 = new Label();
        Label topGameState1 = new Label();
        Label topGameState2 = new Label();
        status1.textProperty().bind(gameState.getVezDe());
        status2.textProperty().bind(gameState.getVezDe());
        topGameState1.textProperty().bind(gameState.getGameStatus());
        topGameState2.textProperty().bind(gameState.getGameStatus());
        HBox topBar1 = new HBox(20, status1, topGameState1);
        HBox topBar2 = new HBox(20, status2, topGameState2);
        VBox statusBox1 = new VBox(20, topBar1);
        VBox statusBox2 = new VBox(20, topBar2);
        statusBox1.setStyle("-fx-alignment: center");
        statusBox2.setStyle("-fx-alignment: center");
        HBox barquinhosPlayer1 = criarBarquinhos(jogador1);
        HBox barquinhosPlayer2 = criarBarquinhos(jogador2);
        HBox gridsP1 = new HBox(20, gridP1, gridP1Oponente);
        HBox gridsP2 = new HBox(20, gridP2, gridP2Oponente);
        VBox player1 = new VBox(gridsP1, barquinhosPlayer1);
        VBox player2 = new VBox(gridsP2, barquinhosPlayer2);
        //HBox colunas = new HBox(40, player1, player2);
        Label mensagem1 = new Label();
        Label mensagem2 = new Label();
        mensagem1.textProperty().bind(Mensagem);
        mensagem2.textProperty().bind(Mensagem);
        VBox Caixamensagem1 = new VBox(20, mensagem1);
        VBox Caixamensagem2 = new VBox(20, mensagem2);
        Caixamensagem1.setStyle("-fx-alignment: center");
        Caixamensagem2.setStyle("-fx-alignment: center");
        VBox root1 = new VBox(20, statusBox1, player1, Caixamensagem1);
        VBox root2 = new VBox(20, statusBox2, player2, Caixamensagem2);
        Scene telaPlayer1 = new Scene(root1, 800, 600);
        Scene telaPlayer2 = new Scene(root2, 800, 600);
        primaryStage.setScene(telaPlayer1);
        secundaryStage.setScene(telaPlayer2);
        primaryStage.setTitle("Player 1");
        secundaryStage.setTitle("Player 2");
        primaryStage.show();
        secundaryStage.show();
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