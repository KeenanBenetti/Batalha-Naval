package com.batalhaNaval.UI;

import com.batalhaNaval.Model.GameState;
import com.batalhaNaval.Model.Player;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static com.batalhaNaval.Controller.GameController.AddListenerCores;
import static com.batalhaNaval.Controller.GameController.ListenerVencedor;
import static com.batalhaNaval.UI.Tabuleiro.*;
import static com.batalhaNaval.UI.Tabuleiro.criarGridPane;

public class TelaDoJogo {
    public static Scene CreateScreen(GameState gameState, Player jogador){

        GridPane gridPlayer = criarGridPane(jogador, "Principal");
        GridPane gridPlayerOponente = criarGridPane(jogador, "Oponente");

        Label status = new Label();
        Label topGameState = new Label();
        status.textProperty().bind(gameState.getVezDe());
        topGameState.textProperty().bind(gameState.getGameStatus());
        HBox topBar = new HBox(20, status, topGameState);
        VBox statusBox = new VBox(20, topBar);
        statusBox.setStyle("-fx-alignment: center");
        HBox barquinhosPlayer = criarBarquinhos(jogador);
        HBox gridsPlayer = new HBox(20, gridPlayer, gridPlayerOponente);
        VBox player = new VBox(gridsPlayer, barquinhosPlayer);
        Label mensagem = new Label();
        mensagem.textProperty().bind(Mensagem);
        VBox Caixamensagem = new VBox(20, mensagem);
        Caixamensagem.setStyle("-fx-alignment: center");
        VBox root = new VBox(20, statusBox, player, Caixamensagem);
        Scene telaPlayer = new Scene(root, 800, 600);

        return telaPlayer;
    }

    public static Scene CreateMenu(GameState gameState, Stage primaryStage, Stage secundaryStage){

        Label Titulo = new Label("Batalha Naval:");
        HBox divTitulo = new HBox();
        divTitulo.getChildren().add(Titulo);

        TextField NomeSinglePlayer = new TextField("Digite Seu Nome:");
        StringProperty nomeSinglePlayer = new SimpleStringProperty();
        nomeSinglePlayer.bind(NomeSinglePlayer.textProperty());
        ToggleGroup Dificuldade = new ToggleGroup();
        RadioButton Facil = new RadioButton("Facil");
        Facil.setToggleGroup(Dificuldade);
        RadioButton Medio = new RadioButton("Medio");
        Medio.setToggleGroup(Dificuldade);
        RadioButton Dificil = new RadioButton("Dificil");
        Dificil.setToggleGroup(Dificuldade);
        Button SinglePlayerBtn = new Button("Jogar Single Player");
        SinglePlayerBtn.setOnAction(event -> {

        });
        VBox SinglePlayer = new VBox(NomeSinglePlayer, SinglePlayerBtn);

        TextField NomeMultiplayerLocal1 = new TextField("Jogador 1:");
        TextField NomeMultiplayerLocal2 = new TextField("Jogador 2");
        StringProperty nomeMultiplayerLocalP1 = new SimpleStringProperty();
        nomeMultiplayerLocalP1.bind(NomeMultiplayerLocal1.textProperty());
        StringProperty nomeMultiplayerLocalP2 = new SimpleStringProperty();
        nomeMultiplayerLocalP2.bind(NomeMultiplayerLocal2.textProperty());

        Button MultiplayerLocalBtn = new Button("Jogar Multiplayer Local");
        MultiplayerLocalBtn.setOnAction(event -> {
            if (!nomeMultiplayerLocalP1.get().isEmpty() && !nomeMultiplayerLocalP2.get().isEmpty()){
                Player jogador1 = new Player(nomeMultiplayerLocalP1.get(), gameState);
                Player jogador2 = new Player(nomeMultiplayerLocalP2.get(), gameState);
                jogador1.setTabuleiroOponente(criarTabuleiroOponente(gameState, jogador1, jogador2));
                jogador2.setTabuleiroOponente(criarTabuleiroOponente(gameState, jogador2, jogador1));
                AddListenerCores(jogador1, "Principal");
                AddListenerCores(jogador2, "Principal");
                AddListenerCores(jogador1, "Oponente");
                AddListenerCores(jogador2, "Oponente");

                gameState.setVezDe(jogador1.getNome());
                gameState.setJogador1Nome(jogador1.getNome());
                gameState.setJogador2Nome(jogador2.getNome());

                primaryStage.setScene(CreateScreen(gameState, jogador1));
                primaryStage.setTitle(jogador1.getNome());
                secundaryStage.setScene(CreateScreen(gameState, jogador2));
                secundaryStage.setTitle(jogador2.getNome());
                ListenerVencedor(gameState, primaryStage, secundaryStage);
                primaryStage.show();
                secundaryStage.show();
            }
        });
        VBox MultiplayerLocal = new VBox(NomeMultiplayerLocal1, NomeMultiplayerLocal2, MultiplayerLocalBtn);

        TextField NomeMultiplayerOnline = new TextField("Digite Seu Nome:");
        StringProperty nomeMultiplayerOnline = new SimpleStringProperty();
        nomeMultiplayerOnline.bind(NomeMultiplayerOnline.textProperty());
        Button MultiplayerOnlineBtn = new Button("Jogar Multiplayer Online");
        MultiplayerOnlineBtn.setOnAction(event -> {

        });
        VBox MultiplayerOnline = new VBox(NomeMultiplayerOnline, MultiplayerOnlineBtn);

        VBox DivModosDeJogo = new VBox(SinglePlayer, MultiplayerLocal, MultiplayerOnline);
        VBox painel = new VBox(divTitulo, DivModosDeJogo);
        VBox root = new VBox(painel);

        return new Scene(root, 800, 600);
    };

}
