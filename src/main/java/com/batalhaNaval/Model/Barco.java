package com.batalhaNaval.Model;


public class Barco {
    public String nome;
    public int tamanho;
    public boolean usado;
    int acertos;
    boolean afundado;

    public Barco(String nome, int tamanho) {
        this.nome = nome;
        this.tamanho = tamanho;
        this.usado = false;
        this.acertos = 0;
    }

    public boolean barcoAfundado(Barco barco){
        barco.acertos += 1;
        if (barco.acertos == tamanho){
            barco.afundado = true;
            return true;
        }
        return false;
    }
}