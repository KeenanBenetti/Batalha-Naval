package com.batalhaNaval;

import javafx.application.Application;
import javafx.stage.Stage;
import java.util.Scanner;
import java.util.Objects;

public class Main extends Application {


    public static void adicionarBarco(int[][] Tabuleiro, int L, int C, String Orientaçao, Barco barco) {
        //Permite adicionar um barco ao tabuleiro se couber
        if (barco.usado) {
            EscreverTela("Esse Barco Já Foi Usado!");
            return;
        }
        int TamanhoBarco = barco.tamanho;
        boolean NaoCabe = false;

        if (Objects.equals(Orientaçao, "Horizontal")) {
            if (C + TamanhoBarco > Tabuleiro[0].length) {
                EscreverTela("Esse barco não cabe aqui!");
                return;
            }

            for (int i = 0; i < TamanhoBarco; i++) {
                int ColunaTeste = C + i;
                if (Tabuleiro[L][ColunaTeste] != 0) {
                    NaoCabe = true;
                    break;
                }
            }

            if (NaoCabe) {
                EscreverTela("O barco selecionado não cabe aqui");
                return;
            }

            for (int i = 0; i < TamanhoBarco; i++) {
                int Coluna = C + i;
                Tabuleiro[L][Coluna] = 2;
            }

            barco.usado = true;

        } else if (Objects.equals(Orientaçao, "Vertical")) {
            if (L + TamanhoBarco > Tabuleiro.length) {
                EscreverTela("Esse barco não cabe aqui!");
                return;
            }

            for (int i = 0; i < TamanhoBarco; i++) {
                int LinhaTeste = L + i;
                if (Tabuleiro[LinhaTeste][C] != 0) {
                    NaoCabe = true;
                    break;
                }
            }

            if (NaoCabe) {
                EscreverTela("O barco selecionado não cabe aqui!");
                return;
            }

            for (int i = 0; i < TamanhoBarco; i++) {
                int Linha = L + i;
                Tabuleiro[Linha][C] = 2;
            }
            barco.usado = true;

        }
    }

    public static void atirar(int[][] Tabuleiro, int L, int C) {
        //Permite verificar se acertou ou não um barco
        if (Tabuleiro[L][C] == 2) {
            Tabuleiro[L][C] = 3;
            AtualizarTela(L, C, "Explosao");
            Println("Acertou");
        } else if (Tabuleiro[L][C] == 0) {
            Tabuleiro[L][C] = 1;
            AtualizarTela(L, C, "Agua");
            Println("Errou");
        }
    }

    public static int JaFoiUsado(int[][] Tabuleiro, int L, int C) {
        //verifica se o local já foi acertado antes
        if (Tabuleiro[L][C] == 1 || Tabuleiro[L][C] == 3) {
            EscreverTela("Já Atirou Aí!");
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

    public static void PrintarTabela(int[][] tabela) {
        for (int i = 0; i < tabela.length; i++) {
            for (int j = 0; j < tabela[0].length; j++) {
                System.out.print(tabela[i][j] + " ");
            }
            System.out.println();
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

    public static void Println(String texto) {
        System.out.println(texto);
    }

    public static void AtualizarTela(int L, int C, String Animaçao) {

    }

    public static void Clear() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

    public static void EscreverTela(String Aviso) {

    }

    public class Valores {
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
        stage.setTitle("Batalha Naval");
        stage.show();
        rodarJogo();
    }

    public static void main(String[] args) {
        launch();
    }
    public void rodarJogo() {
        //Batalha Naval:
        //Objetivo Principal:Criar um programa de batalha naval
        //Objetivos secundarios:
        // conexão multiplayer na rede local via sockets
        // Tela feita usando JavaFX
        // Menu para escolha de multiplayer ou singleplayer
        // tela de configurações do jogo
        // Implementar drag e click para colocar os barcos no tabuleiro

        int[][] tabuleiroPlayer1 = new int[10][10];
        int[][] tabuleiroPlayer2 = new int[10][10];
        Barco[] barcosPlayer1 = new Barco[5];
        Barco[] barcosPlayer2 = new Barco[5];

        CriarBarcos(barcosPlayer1);
        CriarBarcos(barcosPlayer2);
        IniciarTabuleiro(tabuleiroPlayer1);
        IniciarTabuleiro(tabuleiroPlayer2);


        adicionarBarco(tabuleiroPlayer1, 0, 0, "Horizontal", barcosPlayer1[0]);
        adicionarBarco(tabuleiroPlayer1, 1, 0, "Horizontal", barcosPlayer1[1]);
        adicionarBarco(tabuleiroPlayer1, 2, 0, "Horizontal", barcosPlayer1[2]);
        adicionarBarco(tabuleiroPlayer1, 3, 0, "Horizontal", barcosPlayer1[3]);
        adicionarBarco(tabuleiroPlayer1, 4, 0, "Horizontal", barcosPlayer1[4]);

        adicionarBarco(tabuleiroPlayer2, 0, 0, "Horizontal", barcosPlayer2[0]);
        adicionarBarco(tabuleiroPlayer2, 1, 0, "Horizontal", barcosPlayer2[1]);
        adicionarBarco(tabuleiroPlayer2, 2, 0, "Horizontal", barcosPlayer2[2]);
        adicionarBarco(tabuleiroPlayer2, 3, 0, "Horizontal", barcosPlayer2[3]);
        adicionarBarco(tabuleiroPlayer2, 4, 0, "Horizontal", barcosPlayer2[4]);


        Scanner sc = new Scanner(System.in);
        boolean atirou = false;
        boolean won = false;
        while (!won) {

            atirou = false;
            while (!atirou) {
                Clear();
                PrintarTabela(tabuleiroPlayer2);
                Println("Jogador 1: Digite a linha para atirar: ");
                int L = sc.nextInt();
                L -= 1;
                Println("Jogador 1: Digite a coluna para atirar");
                int C = sc.nextInt();
                C -= 1;
                if (JaFoiUsado(tabuleiroPlayer2, L, C) == 0) {
                    atirar(tabuleiroPlayer2, L, C);
                    atirou = true;
                }
                if (atirou && checkarTabelaForWin(tabuleiroPlayer2)) {
                    Println("Jogador 1 ganhou!");
                    won = true;
                }
            }

            if (!won) {
                atirou = false;
                while (!atirou) {
                    Clear();
                    PrintarTabela(tabuleiroPlayer1);
                    Println("Jogador 2: Digite a linha para atirar: ");
                    int L = sc.nextInt();
                    L -= 1;
                    Println("Jogador 2: Digite a coluna para atirar");
                    int C = sc.nextInt();
                    C -= 1;
                    if (JaFoiUsado(tabuleiroPlayer1, L, C) == 0) {
                        atirar(tabuleiroPlayer1, L, C);
                        atirou = true;
                    }
                    if (atirou && checkarTabelaForWin(tabuleiroPlayer1)) {
                        Println("Jogador 2 ganhou!");
                        won = true;
                    }
                }
            }
        }
    }
}