package javapoker.modelos;

public class Bot extends Player {

    //private char jogada;
    private int fichas;

    public Bot(int _fichas) {
        super();
        setBet(0);
        setFichas(_fichas);
    }

    public int escolherJogada() {
        return 1;
    }
}
