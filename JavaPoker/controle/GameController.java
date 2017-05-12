package javapoker.controle;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.Stage;
import javapoker.modelos.Bot;
import javapoker.modelos.Player;
import javapoker.modelos.Table;
import javapoker.view.CardsUI;
import javapoker.view.Cronometro;
import javapoker.view.TableUI;

public class GameController {

    private int raiseAmt;
    private int numeroDeBots;
    private int numeroDeFichas;
    private Bot[] bots;
    private Player jogador;
    private ArrayList<Player> participantes;
    private Table mesa;
    private TableUI mesaVisual;
    private CardsUI cartasVisual;
    private HandRanker handR;
    private Stage cena;
    private GameHandler gH;
    private HandRanker hR;
    private Cronometro relogio;

    public GameController(Stage _cena) {
        cena = _cena;
    }

    public void begin() {

        hR = new HandRanker();
        mesaVisual = new TableUI((numeroDeBots + 1), cena);
        mesaVisual.setPlayerOptions();
        mesa = new Table(numeroDeBots, numeroDeFichas);
        bots = new Bot[numeroDeBots];

        gH = new GameHandler();
        jogador = new Player(numeroDeFichas);

        for (int i = 0; i < numeroDeBots; i++) {
            bots[i] = new Bot(numeroDeFichas);
        }
        participantes = new ArrayList<>();

        participantes.add(jogador);
        for (int i = 0; i < numeroDeBots; i++) {
            participantes.add(bots[i]);
        }

        mesa.getDeck().shuffle();
        gH.GameLoop();
        relogio = new Cronometro();
    }

    public void darCartas() {

        //Printa a carta de todos os players
        participantes.forEach((p) -> {
            String carta1 = mesa.getDeck().giveCards();
            String carta2 = mesa.getDeck().giveCards();
            p.setCartas(carta1, carta2);
            System.out.println("Cartas : " + carta1 + ", " + carta2);
        });
        String[] cartasJ = new String[2];
        cartasJ[0] = jogador.getHand().getFirstCard();
        cartasJ[1] = jogador.getHand().getSecondCard();
        cartasVisual = new CardsUI(cartasJ, mesaVisual.getParticipants());

        participantes.forEach((p) -> {
            p.registerInterest(cartasVisual);
        });
        cartasVisual.colocarCartas(mesaVisual.getVisual());
    }

    synchronized public void EscolheJogada(int participante, boolean turnRiver) {

        //Se for o player mostra as opções na tela.
        if (participante == 0) {
            if (!isFolded(participante)) {
                try {
                    System.out.println("participou");
                    mesaVisual.mostraOpc(true);
                    relogio.setCronometro(mesaVisual.getVisual());
                    wait(30000);
                } catch (InterruptedException ex) {

                }
                relogio.stopTimer();
                if (relogio.getCronometro() <= 0) {
                    ExecutaJogada(1);
                }
                mesaVisual.mostraOpc(false);
            }
        } // se for um bot, por enquanto ta só pagando, mas faz metodo nele depois pra ele escolher a jogada.
        else {
             BotIA iaBot = new BotIA((Bot) participantes.get(gH.getTurn()));
            try {
                iaBot.ArrumaEscolhas(turnRiver);
                wait(2000);
            } catch (InterruptedException ex) {
            }
            ExecutaJogada(iaBot.ChooseJogada());
        }
    }

    synchronized public void ExecutaJogada(int jogada) {
        //pega o jogador da vez.

        int turno = gH.getTurn();
        Player p = participantes.get(turno);
        if (jogada == 0) {
            // só fold.
            System.out.println("Desistiu " + turno);
            p.setFold(true, turno);
            if (turno > 0) {
                try {
                    wait(2000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else if (jogada == 1) {

            //Arrumar pra ver se o pagar não vai detonar as fichas do participante também.
            //Pagar ta errado fion.
            //pq ele ta tirando fichas depois quando o role pq ta mt cagadao
            int aposta = mesa.getBiggestBet() - p.getBet();
            p.tiraFichas(aposta);
            p.setBet(aposta);
            System.out.println("participante " + turno + " pagou " + aposta + " e ainda tem: " + p.getFichas());
        } else if (jogada == 2) {

            //Arrumar essa porcaria pra poder reconhecer um all-win.
            int aposta = (mesa.getBiggestBet() - p.getBet()) + getRaiseAmt();
            p.tiraFichas(aposta);
            p.setBet(aposta);
            System.out.println("participante " + turno + " aumentou em " + getRaiseAmt() + "e ainda tem " + p.getFichas());
            mesa.setBiggestBet(p.getBet());
        }
        if (turno == 0) {
            notifyAll();
        }
    }

    public int getBotsNumber() {
        return numeroDeBots;
    }

    public void setRaiseAmt(int value) {
        raiseAmt = value;
    }

    public int getRaiseAmt() {
        return raiseAmt;
    }

    public void setNumeroDeBots(String _value) {
        numeroDeBots = Integer.parseInt(_value);
    }

    public void setNumeroDeFichas(String _value) {
        numeroDeFichas = Integer.parseInt(_value);
    }

    public void VirarCartas(boolean river) {
        if (river == false) {
            mesa.flop();
            System.out.println("Virou flop: " + mesa.getCardsInTable(0) + ", " + mesa.getCardsInTable(1) + ", " + mesa.getCardsInTable(2));
        } else {
            mesa.turnRiver();
            if (mesa.getCardsInTable(4) == null) {
                System.out.println("Turn: " + mesa.getCardsInTable(3));
            } else {
                System.out.println("Cartas mesa: " + mesa.getCardsInTable(0) + ", " + mesa.getCardsInTable(1)
                        + ", " + mesa.getCardsInTable(2) + ", " + mesa.getCardsInTable(3) + ", " + mesa.getCardsInTable(4));
            }
        }
    }

    public void PagaEntrada(int bBlind) {

        //lógica para pagar big e small blind.
        //trata o caso se os dois ou um deles deu fold.
        int bigBlindV = numeroDeFichas / 10;
        int sB = (bBlind == 0) ? numeroDeBots : bBlind - 1;
        if (!isFolded(bBlind)) {
            participantes.get(bBlind).tiraFichas(bigBlindV);
            participantes.get(bBlind).setBet(bigBlindV);
            mesa.setBiggestBet(bigBlindV);
        } else if (!isFolded(sB) && isFolded(bBlind)) {
            mesa.setBiggestBet(bigBlindV / 2);
        } else {
            mesa.setBiggestBet(0);
        }
        if (!isFolded(sB)) {
            participantes.get(sB).tiraFichas(bigBlindV / 2);
            participantes.get(sB).setBet(bigBlindV / 2);

        }
        System.out.println("Small e big blind pagos");
    }

    public boolean RoundEnd(int i) {
        int valor = mesa.getBiggestBet() * participantes.size();
        if (gH.getTurn() == i && valor % participantes.size() == 0 && CheckBets()) {
            mesa.setPote(valor);
            return true;
        } else {
            return false;
        }
    }

    public boolean isFolded(int pos) {
        return participantes.get(pos).getFold();
    }

    public void RankHands(boolean turnRiver) {

        if (!turnRiver) {
            for (int i = 0; i < participantes.size(); i++) {
                String[] handFlop = new String[5];
                handFlop[0] = participantes.get(i).getHand().getFirstCard();
                handFlop[1] = participantes.get(i).getHand().getSecondCard();
                handFlop[2] = mesa.getCardsInTable(0);
                handFlop[3] = mesa.getCardsInTable(1);
                handFlop[4] = mesa.getCardsInTable(2);
                participantes.get(i).setBestHand(handFlop);
                System.out.println("Rank: " + hR.returnHandRank(handFlop));
            }
        } else {
            //Se o river ainda não tiver virado.
            if (mesa.getCardsInTable(4) == null) {

                for (Player p : participantes) {
                    String[] handFlop = new String[5];
                    handFlop[0] = p.getHand().getFirstCard();
                    handFlop[1] = p.getHand().getSecondCard();
                    int i = 0;
                    int j = 1;
                    int k = 3;
                    boolean terminou = false;
                    while (!terminou) {
                        handFlop[2] = mesa.getCardsInTable(i);
                        handFlop[3] = mesa.getCardsInTable(j);
                        handFlop[4] = mesa.getCardsInTable(k);

                        if (j < 2) {
                            j++;
                        } else if (i < 1) {
                            i++;
                        } else {
                            terminou = true;
                        }
                        if (hR.biggestHand(handFlop, p.getBestHand())) {
                            p.setBestHand(handFlop);
                        }
                    }
                } // quando o river tiver virado.
            } else {
                for (Player p : participantes) {
                    String[] handFlop = new String[5];
                    int i = 0;
                    int j = 1;
                    int k = 4;
                    boolean terminou = false;
                    handFlop[0] = p.getHand().getFirstCard();
                    handFlop[1] = p.getHand().getSecondCard();
                    while (!terminou) {
                        handFlop[2] = mesa.getCardsInTable(i);
                        handFlop[3] = mesa.getCardsInTable(j);
                        handFlop[4] = mesa.getCardsInTable(k);

                        if (j < 3) {
                            j++;
                        } else if (i < 1) {
                            j = 2;
                            i++;
                        } else if (i < 2 && j == 3) {
                            i++;
                        } else {
                            terminou = true;
                        }
                        if (hR.biggestHand(handFlop, p.getBestHand())) {
                            p.setBestHand(handFlop);
                        }
                    }
                }
            }
        }
    }

    public void PegarVencedor() {

        int i = 0;
        int k = 1;
        while (k < numeroDeBots + 1) {

            if (hR.biggestHand(participantes.get(i).getBestHand(), participantes.get(k).getBestHand()) && !isFolded(i)) {
                k++;
            } else {
                i = k;
                k++;
            }
        }
        System.out.println("VENCEDOR JOGADOR NUMERO: " + (i + 1));
        participantes.get(i).addFichas(mesa.getPote());
        if (!EndGame()) {
            System.out.println("A Começar uma nova rodada");
            gH.GameLoop();
        }
    }

    public void ZerarApostas() {
        participantes.forEach((p) -> {
            p.setBet((-1) * p.getBet());
        });
    }

    public boolean CheckBets() {
        int contador = 0;
        for (Player p : participantes) {
            if (p.getBet() == mesa.getBiggestBet()) {
                contador++;
            }
        }
        if (contador == participantes.size()) {
            return true;
        }
        return false;
    }

    public boolean EndGame() {
        int counter = 0;
        System.out.println("Verificando se acabou o jogo.");
        for (int i = 0; i < participantes.size(); i++) {
            if (participantes.get(i).getFichas() > 0) {
                counter++;
            }
        }
        if (counter >= 2) {
            System.out.println("O jogo não acabou ainda.");
            return false;
        } else {
            System.out.println("O jogo acabou.");
            return true;
        }
    }
}
