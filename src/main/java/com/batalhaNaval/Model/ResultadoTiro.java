package com.batalhaNaval.Model;

public class ResultadoTiro {
    public boolean Acertou;
    public boolean Afundou;
    public boolean Venceu;

    public ResultadoTiro(boolean acertou, boolean afundou, boolean venceu) {
        Acertou = acertou;
        Afundou = afundou;
        Venceu = venceu;
    }
}
