package com.batalhaNaval.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Celula {
    public StringProperty status = new SimpleStringProperty();
    int L;
    int C;
    Barco barco;
    String orientacaoBarco;
    int pedacoN;

    public Celula(String status, int L, int C) {
        this.status.set(status);
        this.L = L;
        this.C = C;
    }

    public void setBarco(Barco barco, String orientacaoBarco, int pedacoN) {
        this.barco = barco;
        this.orientacaoBarco = orientacaoBarco;
        this.pedacoN = pedacoN;
    }
}