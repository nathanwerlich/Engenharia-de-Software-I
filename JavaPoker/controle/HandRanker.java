package javapoker.controle;

import java.util.Arrays;
import java.util.Comparator;

//VAI SER A CLASSE QUE VAI DAR UM "VALOR PARA AS MÃOS" E VAI TER UM MÉTODO PARA COMPARA-LAS E RETORNAR A MAIOR.
// FAZER AQUELE ENUMERADO DO LINK QUE EU MANDEI PRO FAUSTO.
// TERMINANDO ISSO AQUI VAI FALTA SÓ AS PARADAS TEÓRICAS QUE SE EU TERMINAR ISSO AQUI EU VOU TER TERMINADO O PADRÃO DE PROJETO
// E O SEGUNDO CASO DE USO.
public class HandRanker {

    public Comparator<String> byCard = (String esquerda, String direita) -> {
        if (pegaValor(esquerda) < pegaValor(direita)) {
            return -1;
        } else {
            return 1;
        }
    };

    public HandRanker() {
    }

    public int pegaValor(String carta) {

        if (carta.contains("A") || carta.contains("K") || carta.contains("Q") || carta.contains("J")) {
            String subs = carta.substring(0, 1);
            int valor = 0;
            switch (subs) {

                case "A":
                    valor = 14;
                    break;
                case "K":
                    valor = 13;
                    break;
                case "Q":
                    valor = 12;
                    break;
                case "J":
                    valor = 11;
                    break;
            }
            return valor;
        } else {
            if (carta.substring(1, 2).contains(" ")) {
                return Integer.parseInt(carta.substring(0, 1));
            } else {
                return Integer.parseInt(carta.substring(0, 2));
            }

        }
    }

    public enum HandRank {
        ROYAL_FLUSH, STRAIGHT_FLUSH, FOUR_OF_A_KIND, FULL_HOUSE, FLUSH, STRAIGHT, THREE_OF_A_KIND, TWO_PAIR, PAIR, HIGH_CARD;
    }

    // Método que vai testar tipo por tipo pra ver se a mão mais 3 cartas se
    // encaixa em algum tipo de rank de mãos;
    public HandRank returnHandRank(String[] cartas) {
        Arrays.sort(cartas, byCard);

        if (isARoyaFlush(cartas)) {
            return HandRank.ROYAL_FLUSH;
        } else if (isAStraightFlush(cartas)) {
            return HandRank.STRAIGHT_FLUSH;
        } else if (isAFourOfAKind(cartas)) {
            return HandRank.FOUR_OF_A_KIND;
        } else if (isAFullHouse(cartas)) {
            return HandRank.FULL_HOUSE;
        } else if (isAFlush(cartas)) {
            return HandRank.FLUSH;
        } else if (isAStraight(cartas)) {
            return HandRank.STRAIGHT;
        } else if (isAThreeOfAKind(cartas)) {
            return HandRank.THREE_OF_A_KIND;
        } else if (isATwoPair(cartas)) {
            return HandRank.TWO_PAIR;
        } else if (isAPair(cartas)) {
            return HandRank.PAIR;
        } else {
            return HandRank.HIGH_CARD;
        }
    }

    private boolean isAPair(String[] cartas) {

        int i = 0;

        while (i < cartas.length - 1) {
            if (pegaValor(cartas[i]) == pegaValor(cartas[i + 1])) {
                return true;
            } else {
                i++;
            }
        }
        return false;
    }

    private boolean isATwoPair(String[] cartas) {
        int i = 0;
        int pares = 0;
        while (i < cartas.length - 1) {
            if (pegaValor(cartas[i]) == pegaValor(cartas[i + 1])) {
                pares++;
                i++;
            } else {
                i++;
            }
        }
        if (pares == 2) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isAThreeOfAKind(String[] cartas) {

        int i = 0;

        while (i < cartas.length - 2) {
            if (pegaValor(cartas[i]) == pegaValor(cartas[i + 1])
                    && (pegaValor(cartas[i + 1]) == pegaValor(cartas[i + 2]))) {
                return true;
            } else {
                i++;
            }
        }
        return false;
    }

    private boolean isAStraight(String[] cartas) {

        if ((pegaValor(cartas[4]) == (pegaValor(cartas[3]) + 1)) && (pegaValor(cartas[3]) == (pegaValor(cartas[2]) + 1))
                && (pegaValor(cartas[2]) == (pegaValor(cartas[1]) + 1))
                && (pegaValor(cartas[1]) == (pegaValor(cartas[0]) + 1))) {
            return true;
        }
        return false;
    }

    private boolean isAFlush(String[] cartas) {

        if (cartas[0].contains("Diamonds") && cartas[1].contains("Diamonds") && cartas[2].contains("Diamonds")
                && cartas[3].contains("Diamonds") && cartas[4].contains("Diamonds")) {
            return true;
        } else if (cartas[0].contains("Clubs") && cartas[1].contains("Clubs") && cartas[2].contains("Clubs")
                && cartas[3].contains("Clubs") && cartas[4].contains("Clubs")) {
            return true;
        } else if (cartas[0].contains("Hearts") && cartas[1].contains("Hearts") && cartas[2].contains("Hearts")
                && cartas[3].contains("Hearts") && cartas[4].contains("Hearts")) {
            return true;
        } else if (cartas[0].contains("Spades") && cartas[1].contains("Spades") && cartas[2].contains("Spades")
                && cartas[3].contains("Spades") && cartas[4].contains("Spades")) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isAFourOfAKind(String[] cartas) {
        int i = 0;

        while (i < cartas.length - 3) {
            if (pegaValor(cartas[i]) == pegaValor(cartas[i + 1])
                    && (pegaValor(cartas[i + 1]) == pegaValor(cartas[i + 2])) && (pegaValor(cartas[i + 2]) == pegaValor(cartas[i + 3]))) {
                return true;
            } else {
                i++;
            }
        }
        return false;
    }

    private boolean isAFullHouse(String[] cartas) {
        boolean temPar = false, temTrinca = false;
        temPar = isAPair(cartas);
        temTrinca = isAThreeOfAKind(cartas);
        return (temPar && temTrinca);
    }

    private boolean isAStraightFlush(String[] cartas) {
        boolean temStraight = false, temFlush = false;
        temStraight = isAStraight(cartas);
        temFlush = isAFlush(cartas);
        return (temStraight && temFlush);

    }

    private boolean isARoyaFlush(String[] cartas) {
        boolean temA = false, temRei = false, temRainha = false, temValete = false, temDez = false;
        temA = pegaValor(cartas[4]) == 14;
        temRei = pegaValor(cartas[3]) == 13;
        temRainha = pegaValor(cartas[2]) == 12;
        temValete = pegaValor(cartas[1]) == 11;
        temDez = pegaValor(cartas[0]) == 10;
        return ((temA && temRei) && (temRainha && temValete) && temDez);
    }

    public boolean biggestHand(String[] mao1, String[] mao2) {
        HandRank h1 = returnHandRank(mao1), h2 = returnHandRank(mao2);
        //se o ordinal da mão 1 for menor que o da mao2 significa que a mao1 vale mais que a 2.
        if (h1.ordinal() < h2.ordinal() || pegaValor(mao1[4]) > pegaValor(mao2[4])) {
            return true;
        } else {
            return false;
        }
    }
}
