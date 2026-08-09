package com.batalhaNaval.Model;

public class Celula {
    public String status;
    int L;
    int C;
    Barco barco;
    String orientacaoBarco;
    int pedacoN;

    public Celula(String status, int L, int C) {
        this.status = status;
        this.L = L;
        this.C = C;
    }

    public void setBarco(Barco barco, String orientacaoBarco, int pedacoN) {
        this.barco = barco;
        this.orientacaoBarco = orientacaoBarco;
        this.pedacoN = pedacoN;
    }
}