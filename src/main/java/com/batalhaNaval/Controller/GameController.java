package com.batalhaNaval.Controller;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import com.batalhaNaval.Model.Barco;
import static com.batalhaNaval.UI.Tabuleiro.MensagemTela;

public class GameController {

    public static boolean adicionarBarco(int[][] Tabuleiro, int L, int C, StringProperty Orientaçao, Barco barco) {
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

    public static void atirar(int[][] Tabuleiro, int L, int C, String GameStatus) {
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

    public static void CriarBarcos(Barco[] barcos) {
        barcos[0] = new Barco("Porta-aviões", 5);
        barcos[1] = new Barco("Encouraçado", 4);
        barcos[2] = new Barco("Cruzador", 3);
        barcos[3] = new Barco("Submarino", 3);
        barcos[4] = new Barco("Destroyer", 2);
    }

    public static boolean checkarStatusPlayer(Barco[] barcosPlayerAtual){
        for (Barco barco : barcosPlayerAtual) {
            if (!barco.usado) {
                return false;
            }
        }
        return true;
    }

//    public boolean checkarStatusPlayers(boolean Player1Done, boolean Player2Done){
//        return Player1Done && Player2Done;
//    }

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

    public static void trocarOrientaçao(StringProperty Orientaçao){
        if (Orientaçao.get().equals("Horizontal")){
            Orientaçao.set("Vertical");

        } else if (Orientaçao.get().equals("Vertical")) {
            Orientaçao.set("Horizontal");
        }
    }
}
