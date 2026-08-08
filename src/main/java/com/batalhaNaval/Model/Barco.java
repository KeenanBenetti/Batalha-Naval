package com.batalhaNaval.Model;


public class Barco {
    public String nome;
    public int tamanho;
    public boolean usado;

    public Barco(String nome, int tamanho) {
        this.nome = nome;
        this.tamanho = tamanho;
        this.usado = false;
    }
}