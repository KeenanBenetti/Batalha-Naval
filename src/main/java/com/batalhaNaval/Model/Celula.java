package com.batalhaNaval.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Celula {
    public StringProperty status = new SimpleStringProperty();
    public StringProperty hover = new SimpleStringProperty();
    int L;
    int C;
    Barco barco;
    String orientacaoBarco;
    int pedacoN;

    public Celula(String status, int L, int C) {
        this.status.set(status);
        this.hover.set("");
        this.L = L;
        this.C = C;
    }

    public void setBarco(Barco barco, String orientacaoBarco, int pedacoN) {
        this.barco = barco;
        this.orientacaoBarco = orientacaoBarco;
        this.pedacoN = pedacoN;
    }

    public Barco getBarco() {
        return barco;
    }

    public String getOrientacaoBarco() {
        return orientacaoBarco;
    }

    public int getPedacoN() {
        return pedacoN;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }
}