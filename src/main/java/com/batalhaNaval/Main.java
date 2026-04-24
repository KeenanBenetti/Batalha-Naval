
public static void adicionarBarco(int[][] Tabuleiro, int L, int C, String Orientaçao, int TamanhoBarco){
    //Permite adicionar um barco ao tabuleiro se couber
    boolean NaoCabe = false;
    if (Objects.equals(Orientaçao, "horizontal")){
        for (int i=0; i < TamanhoBarco; i++){
            int ColunaTeste = C +i;
            if (Tabuleiro[L][ColunaTeste] == 0)
                NaoCabe = false;
            else{
                NaoCabe = true;
            }
        }
        if (NaoCabe){
            EscreverTela("O barco selecionado não cabe aqui");
        } else {
            for (int i=0; i < TamanhoBarco; i++){
                int Coluna = C +i;
                Tabuleiro[L][Coluna] = 2;
            }
            ConsumirBarco(TamanhoBarco);
        }
    } else if (Objects.equals(Orientaçao, "vertical")) {
        for (int i=0; i < TamanhoBarco; i++){
            int LinhaTeste = L +i;
            if (Tabuleiro[LinhaTeste][C] == 0)
                NaoCabe = false;
            else{
                NaoCabe = true;
            }
        }
        if (NaoCabe){
            EscreverTela("O barco selecionado não cabe aqui!");
        } else {
            for (int i=0; i < TamanhoBarco; i++){
                int Linha = L +i;
                Tabuleiro[Linha][C] = 2;
            }
            ConsumirBarco(TamanhoBarco);
        }
    }
}

public static void atirar(int[][] Tabuleiro ,int L, int C){
    //Permite verificar se acertou ou não um barco
    if (Tabuleiro[L][C] == 2){
        Tabuleiro[L][C] = 3;
        AtualizarTela(L,C, "Explosao");
    } else if (Tabuleiro[L][C] == 0) {
        Tabuleiro[L][C] = 1;
        AtualizarTela(L,C, "Agua");
    }
}

public static int JaFoiUsado(int[][] Tabuleiro, int L, int C){
    //verifica se o local já foi acertado antes
    if (Tabuleiro[L][C] == 1 || Tabuleiro[L][C] == 3){
        EscreverTela("Já Atirou Aí!");
        return 1;
    }
    else {
        return 0;
    }
}

public static void ConsumirBarco(int TamanhoBarco){

}

public void CriarBarcos(Barco[] barcos){
    barcos[0] = new Barco("Porta-aviões", 5);
    barcos[1] = new Barco("Encouraçado", 4);
    barcos[2] = new Barco("Cruzador", 3);
    barcos[3] = new Barco("Submarino", 3);
    barcos[4] = new Barco("Destroyer", 2);
}

public void IniciarTabuleiro(int [][] Tabuleiro){
    for (int i =0; i<Tabuleiro.length;i++){
        for (int ii = 0; ii < Tabuleiro[0].length; ii++) {
            Tabuleiro[i][ii] = 0;
        }
    }
}

public static void AtualizarTela(int L, int C, String Animaçao){

}

public static void EscreverTela(String Aviso){

}
public class Valores {
    public static final int AGUA = 0;
    public static final int USADO = 1;
    public static final int BARCO = 2;
    public static final int ACERTO = 3;
}
class Barco{
    String nome;
    int tamanho;
    Barco(String nome, int tamanho){
        this.nome = nome;
        this.tamanho = tamanho;
    }
}

void main() {
    //Batalha Naval:
    //Objetivo Principal:Criar um programa de batalha naval
    //Objetivos secundarios:
    // conexão multiplayer na rede local via sockets
    // Tela feita usando JavaFX
    // Menu para escolha de multiplayer ou singleplayer
    // tela de configurações do jogo
    // Implementar drag e click para colocar os barcos no tabuleiro

    int [][] tabuleiroPlayer1 = new int[10][10];
    int [][] tabuleiroPlayer2 = new int[10][10];
    Barco [] barcosPlayer1 = new Barco [5];
    Barco [] barcosPlayer2 = new Barco [5];

    CriarBarcos(barcosPlayer1);
    CriarBarcos(barcosPlayer2);
    IniciarTabuleiro(tabuleiroPlayer1);
    IniciarTabuleiro(tabuleiroPlayer2);


}