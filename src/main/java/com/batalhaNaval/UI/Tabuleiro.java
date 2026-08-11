package com.batalhaNaval.UI;

import com.batalhaNaval.Model.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import static com.batalhaNaval.Controller.GameController.*;
import static com.batalhaNaval.Controller.GameController.adicionarBarco;
import static com.batalhaNaval.Controller.GameController.checkarEspaçoParaBarco;


public class Tabuleiro {

    public static StringProperty Mensagem = new SimpleStringProperty();

    public static void MensagemTela(String texto) {
        Mensagem.set(texto);
    }

    public static void ReiniciarJogo(){
        //futuramente irá reiniciar o jogo inteiro
    }

    public static void TelaVencedor(String vencedor, Stage telaJogador1, Stage telaJogador2) {
        Label aviso1 = new Label("O vencedor é " + vencedor + "!");
        Label aviso2 = new Label("O vencedor é " + vencedor + "!");
        aviso1.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        aviso2.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        Button reiniciar1 = new Button("Reiniciar");
        Button reiniciar2 = new Button("Reiniciar");
        reiniciar1.setOnAction(e -> {
            ReiniciarJogo();
        });
        reiniciar2.setOnAction(e -> {
            ReiniciarJogo();
        });
        Button fechar1 = new Button("Fechar Jogo");
        Button fechar2 = new Button("Fechar Jogo");
        fechar1.setOnAction(e -> System.exit(0));
        fechar2.setOnAction(e -> System.exit(0));
        HBox botoes1 = new HBox(20, reiniciar1, fechar1);
        HBox botoes2 = new HBox(20, reiniciar2, fechar2);
        VBox root1 = new VBox(20, aviso1, botoes1);
        VBox root2 = new VBox(20, aviso2, botoes2);
        Scene cena1 = new Scene(root1, 400, 200);
        Scene cena2 = new Scene(root2, 400, 200);
        telaJogador1.setScene(cena1);
        telaJogador2.setScene(cena2);
    }

    public static Button[][] criarTabuleiro(GameState gameState, Player jogador){
        Button[][] tabuleiro = new Button[10][10];
        for (int i = 0; i < 10; i++){
            for (int j = 0; j < 10; j++){
                Button btn = new Button();
                btn.setPrefSize(40, 40);
                int L = i;
                int C = j;

                btn.setUserData(new Celula("Agua", L, C));
                btn.setStyle("-fx-background-size:cover;" + "-fx-background-image: url('/Agua.gif'); ");

                tabuleiro[i][j] = btn;

                btn.setOnMouseEntered(e -> {
                    if (gameState.getVezDe().get().equals(jogador.getNome()) && gameState.getGameStatus().get().equals("Setup")){
                        if (!jogador.getBarcoSelecionado().usado){
                            if (checkarEspaçoParaBarco(L, C, jogador)){
                                AdicionarHoverBarco(L, C, jogador);
                            }
                        }
                    }
                });

                btn.setOnMouseExited(e -> {
                    if (gameState.getVezDe().get().equals(jogador.getNome()) && gameState.getGameStatus().get().equals("Setup")){
                        if (!jogador.getBarcoSelecionado().usado){
                            if (checkarEspaçoParaBarco(L, C, jogador)){
                                RemoverHoverBarco(L, C, jogador);
                            }
                        }
                    }
                });

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
                btn.setStyle("-fx-background-size:cover;" + "-fx-background-image: url('/Agua.gif'); ");

                tabuleiro[i][j] = btn;

                btn.setOnAction(e ->{
                    if (!gameState.getVezDe().get().equals(jogador.getNome())){
                        MensagemTela("Não é sua vez, aguarde!");
                        return;
                    }
                    if (gameState.getGameStatus().get().equals("Ready")) {
                        if(checkarTabelaParaAtirar(L, C, jogador)){
                            String resultado = AtirarNoOponente(L, C, jogador, oponente, gameState);
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

    public static void mudarCorCelula(Button btn, String qualTabela){
        Celula celula = (Celula) btn.getUserData();

        switch (celula.status.get()) {
            //case "Agua" -> btn.setStyle("-fx-background-color: lightblue;");
            case "Agua" -> btn.setStyle("-fx-background-size:cover;" + "-fx-background-image: url('/Agua.gif'); ");
            case "Barco" -> {
                if(qualTabela.equals("Principal")){
                    btn.setStyle("-fx-background-color: gray;");
                }
            }
            case "Acerto" -> {
                //btn.setStyle("-fx-background-image: url('/Explosao.gif');");
                RodarAnimação("Explosao", btn);
            }
            case "Errou" -> {
                ColorAdjust escurecer = new ColorAdjust();
                escurecer.setBrightness(-0.2);
                btn.setStyle("-fx-background-size:cover;" + "-fx-background-image: url('/Agua.gif'); ");
                btn.setEffect(escurecer);
            }
        }
    }

    public static void RodarAnimação(String qualAnimacao, Button btn){
        int frame[] = {0};
        int Nframes = 0;
        if (qualAnimacao.equals("Explosao")) {
            Nframes = 12;
        }
        String imagem[] = {""};

        Timeline timeline = new Timeline(
            new KeyFrame(Duration.millis(100), e -> {
                imagem[0] = "sprite_" + frame[0] + ".png";
                btn.setStyle("-fx-background-size:cover;" + "-fx-background-image: url('/Explosao/" + imagem[0] + "');");
                frame[0] ++;
            })
        );

        timeline.setCycleCount(Nframes);
        timeline.play();
    }

}
