package javapoker.controle;

public class GameHandler {

    private boolean entrada;
    private boolean giveCards;
    private int turn;
    private int bBlind;
    
    public GameHandler() {
        giveCards = true;
        turn = 0;
        bBlind = 0;
    }

    public void GameLoop() {

        System.out.println("Começou uma nova rodada");
        if (giveCards) {
            Main.gCont.darCartas();
            giveCards = false;
        }

        new java.util.Timer().schedule(
                new java.util.TimerTask() {
            @Override
            public void run() {
                int contador = 0;
                int cards = 1;
                boolean turnRiver = false;
                boolean rodada = true;
                while (rodada) {
                    if (entrada == false) {

                        // vê se ta na hora de pagar os blinds.
                        Main.gCont.PagaEntrada(bBlind);
                        entrada = true;
                    }
                    Main.gCont.EscolheJogada(turn, turnRiver);
                    if (Main.gCont.RoundEnd(bBlind + 1) && contador > Main.gCont.getBotsNumber()) {
                        //zera o turn, vira flop turn river, se o river já tiver sido virado, ve quem ganhou.
                        if (cards < 4) {
                            System.out.println("Fim da rodada.");
                            contador = 0;
                            Main.gCont.VirarCartas(turnRiver);
                            Main.gCont.ZerarApostas();
                            Main.gCont.RankHands(turnRiver);
                            if (bBlind == Main.gCont.getBotsNumber()) {
                                turn = 0;
                            } else {
                                turn = bBlind + 1;
                            }
                            entrada = false;
                            turnRiver = true;
                            cards++;
                        } else {
                            cards = 0;
                            rodada = false;
                            bBlind++;
                            giveCards = true;
                            Main.gCont.PegarVencedor();
                            break;
                        }
                    } else {
                        turn++;
                        contador++;
                    }
                    if (turn > Main.gCont.getBotsNumber()) {
                        turn = 0;
                    }
                }
            }
        },
                2000
        );

    }

    public int getTurn() {
        return turn;
    }
}
