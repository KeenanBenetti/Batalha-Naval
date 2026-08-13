package com.batalhaNaval;

import com.batalhaNaval.Model.GameState;
import com.batalhaNaval.Model.Player;
import javafx.application.Application;
import javafx.stage.Stage;

import static com.batalhaNaval.Controller.GameController.*;
import static com.batalhaNaval.UI.Tabuleiro.*;
import static com.batalhaNaval.UI.TelaDoJogo.*;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

        Stage secundaryStage = new Stage();

        GameState gameState = new GameState();
        primaryStage.setScene(CreateMenu(gameState, primaryStage, secundaryStage));

        primaryStage.show();
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
