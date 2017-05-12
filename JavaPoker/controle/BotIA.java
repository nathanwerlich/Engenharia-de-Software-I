package javapoker.controle;

import java.util.Random;
import javapoker.controle.HandRanker.HandRank;
import javapoker.modelos.Bot;

//por enquanto o aumentar do bot não está funcionando eu tenho que pensar em uma forma de fazer isso direito ainda.
public class BotIA {

    private int[] chances;
    private Bot bot;
    private Random gerador;
    private int scareLevel, confidenceLevel, safeLevel;

    public BotIA(Bot _bot) {
        chances = new int[10];
        gerador = new Random();
        bot = _bot;
    }

    public void ArrumaEscolhas(boolean turnRiver) {

        //Começa com as duas cartas da mão, se elas forem altas ele tem uma boa chance de aumentar ou pagar mas não de correr
        //se o valor da mão estiver entre 13 e 22 ele vai ter uma grande chance de pagar, se for maior que isso ele tem uma grande chance de aumentar se for menor
        // uma grande chance de correr.
        HandRanker hR = new HandRanker();
        if (!turnRiver) {
            int val = 0;
            val += hR.pegaValor(bot.getHand().getFirstCard());
            val += hR.pegaValor(bot.getHand().getSecondCard());
            if (val <= 13) {
                switch (val) {
                    case 13:
                        scareLevel = 3;
                        safeLevel = 7;
                        break;
                    case 12:
                        scareLevel = 3;
                        safeLevel = 6;
                        confidenceLevel = 1;
                        break;
                    case 11:
                        scareLevel = 4;
                        safeLevel = 6;
                        break;
                    case 10:
                        scareLevel = 4;
                        safeLevel = 5;
                        confidenceLevel = 1;
                        break;
                    case 9:
                        scareLevel = 5;
                        safeLevel = 5;
                        break;
                    case 8:
                        scareLevel = 5;
                        safeLevel = 4;
                        confidenceLevel = 1;
                        break;
                    case 7:
                        scareLevel = 6;
                        safeLevel = 4;
                        break;
                    case 6:
                        scareLevel = 6;
                        safeLevel = 3;
                        confidenceLevel = 1;
                        break;
                    case 5:
                        scareLevel = 7;
                        safeLevel = 3;
                        break;
                    case 4:
                        scareLevel = 7;
                        safeLevel = 2;
                        confidenceLevel = 1;
                        break;
                }
                for (int i = 0; i < scareLevel; i++) {
                    chances[i] = 0;
                }
                for (int i = scareLevel; i < scareLevel + safeLevel; i++) {
                    chances[i] = 1;
                }
                if (scareLevel + safeLevel < chances.length - 1) {
                    int j = scareLevel + safeLevel;
                    for (int i = j; i < j - confidenceLevel; i++) {
                        chances[i] = 1;
                    }
                }
            } else if (val > 13 && val <= 22) {
                switch (val) {
                    case 14:
                        scareLevel = 2;
                        safeLevel = 6;
                        confidenceLevel = 2;
                        break;
                    case 15:
                        scareLevel = 2;
                        safeLevel = 7;
                        confidenceLevel = 1;
                        break;
                    case 16:
                        scareLevel = 2;
                        safeLevel = 6;
                        confidenceLevel = 2;
                        break;
                    case 17:
                        safeLevel = 8;
                        confidenceLevel = 2;
                        break;
                    case 18:
                        scareLevel = 1;
                        safeLevel = 8;
                        confidenceLevel = 1;
                        break;
                    case 19:
                        scareLevel = 1;
                        safeLevel = 9;
                        break;
                    case 20:
                        scareLevel = 1;
                        safeLevel = 7;
                        confidenceLevel = 2;
                        break;
                    case 21:
                        safeLevel = 7;
                        confidenceLevel = 3;
                    case 22:
                        safeLevel = 8;
                        confidenceLevel = 2;
                        break;
                }
                for (int i = 0; i < safeLevel; i++) {
                    chances[i] = 1;
                }
                for (int i = safeLevel; i < safeLevel + confidenceLevel; i++) {
                    chances[i] = 1;
                }
                if (safeLevel + confidenceLevel < chances.length - 1) {
                    for (int i = safeLevel + confidenceLevel; i < (confidenceLevel + safeLevel) - scareLevel; i++) {
                        chances[i] = 0;
                    }
                }
            } else {
                switch (val) {
                    case 23:
                        scareLevel = 2;
                        safeLevel = 4;
                        confidenceLevel = 4;
                        break;
                    case 24:
                        scareLevel = 1;
                        safeLevel = 5;
                        confidenceLevel = 4;
                        break;
                    case 25:
                        scareLevel = 1;
                        safeLevel = 4;
                        confidenceLevel = 5;
                        break;
                    case 26:
                        safeLevel = 5;
                        confidenceLevel = 5;
                        break;
                }
                for (int i = 0; i < safeLevel; i++) {
                    chances[i] = 1;
                }
                for (int i = safeLevel; i < safeLevel + confidenceLevel; i++) {
                    chances[i] = 1;
                }
                if (safeLevel + confidenceLevel < chances.length - 1) {
                    for (int i = safeLevel + confidenceLevel; i < (confidenceLevel + safeLevel) - scareLevel; i++) {
                        chances[i] = 0;
                    }
                }
            }
        } else {
            HandRank handR = hR.returnHandRank(bot.getBestHand());
            switch (handR.ordinal()) {
                case 1:
                    confidenceLevel = 10;
                    break;
                case 2:
                    confidenceLevel = 9;
                    safeLevel = 1;
                    break;
                case 3:
                    confidenceLevel = 8;
                    safeLevel = 2;
                    break;
                case 4:
                    confidenceLevel = 7;
                    safeLevel = 3;
                    break;
                case 5:
                    confidenceLevel = 6;
                    safeLevel = 4;
                    break;
                case 6:
                    confidenceLevel = 5;
                    scareLevel = 2;
                    safeLevel = 3;
                    break;
                case 7:
                    confidenceLevel = 4;
                    safeLevel = 4;
                    scareLevel = 2;
                    break;
                case 8:
                    confidenceLevel = 3;
                    safeLevel = 5;
                    scareLevel = 2;
                    break;
                case 9:
                    confidenceLevel = 1;
                    safeLevel = 4;
                    scareLevel = 5;
                    break;
                case 10:
                    scareLevel = 6;
                    safeLevel = 4;
                    break;
            }
            if (confidenceLevel < 10 && confidenceLevel > 0) {
                for (int i = 0; i < confidenceLevel; i++) {
                    chances[i] = 1;
                }
                for (int i = confidenceLevel; i < confidenceLevel + safeLevel; i++) {
                    chances[i] = 1;
                }
                if (confidenceLevel + safeLevel < chances.length - 1) {
                    for (int i = confidenceLevel + safeLevel; i < (confidenceLevel + safeLevel) - scareLevel; i++) {
                        chances[i] = 0;
                    }
                }
            } else if (confidenceLevel == 0) {
                for (int i = 0; i < scareLevel; i++) {
                    chances[i] = 0;
                }
                for (int i = scareLevel; i < chances.length; i++) {
                    chances[i] = 1;
                }
            } else {
                for (int i = 0; i < chances.length; i++) {
                    chances[i] = 1;
                }
            }
        }
    }

    public int ChooseJogada() {
        return chances[gerador.nextInt(10)];
    }

}
