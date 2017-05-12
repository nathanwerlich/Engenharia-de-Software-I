package javapoker.view;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javapoker.interfaces.Observer;

public class CardsUI implements Observer {

    private ImageView[] participantes;
    private ImageView[] cardsBack;
    private ImageView[] cartas;
    private String[] cartasDoJogador;
    private Group root;

    public CardsUI(String[] _cards, ImageView[] participants) {
        cartasDoJogador = _cards;
        participantes = participants;
    }

    /*Método para colocar o grafico das cartas na mesa.*/
    public void colocarCartas(Group _root) {
        root = _root;
        //E fazer o gameLoop em geral funcionar novamente também.
        /*Setando as cartas do jogador.*/
        Platform.runLater(() -> {
            Image carta1 = new Image("javapoker/imgs/" + cartasDoJogador[0]);
            Image carta2 = new Image("javapoker/imgs/" + cartasDoJogador[1]);
            int i = 0;
            cartas = new ImageView[2];
            cartas[0] = new ImageView();
            cartas[0].setImage(carta1);
            root.getChildren().add(cartas[0]);
            cartas[1] = new ImageView();
            cartas[1].setImage(carta2);
            root.getChildren().add(cartas[1]);

            cardsBack = new ImageView[(participantes.length - 1) * 2];
            /*Setando as cartas dos bots*/
            for (int k = 0; k < cardsBack.length; k++) {
                cardsBack[k] = new ImageView();
                cardsBack[k].setImage(new Image("javapoker/imgs/cardBack" + (i + 1)));
                if (i == 1) {
                    i = 0;
                } else {
                    i++;
                }
                root.getChildren().add(cardsBack[k]);
            }

            /*Posicionando as cartas do jogador*/
            cartas[0].setLayoutX(participantes[0].getX() + 45);
            cartas[1].setLayoutX(participantes[0].getX() + 80);
            cartas[0].setLayoutY(participantes[0].getY() - 80);
            cartas[1].setLayoutY(participantes[0].getY() - 80);

            /*Posicionando as cartas dos bots*/
            int l = 1;
            for (int j = 0; j < cardsBack.length - 1; j += 2) {
                switch (j) {
                    case 0:
                        cardsBack[j].setLayoutX(participantes[l].getX());
                        cardsBack[j].setLayoutY(participantes[l].getY() - 20);
                        cardsBack[(j + 1)].setLayoutX(participantes[l].getX() - 40);
                        cardsBack[(j + 1)].setLayoutY(participantes[l].getY() - 20);
                        break;
                    case 2:
                        cardsBack[j].setLayoutX(participantes[l].getX());
                        cardsBack[j].setLayoutY(participantes[l].getY() + 20);
                        cardsBack[(j + 1)].setLayoutX(participantes[l].getX() - 40);
                        cardsBack[(j + 1)].setLayoutY(participantes[l].getY() + 20);
                        break;
                    case 4:
                        cardsBack[j].setLayoutX(participantes[l].getX() + 45);
                        cardsBack[j].setLayoutY(participantes[l].getY() + 80);
                        cardsBack[(j + 1)].setLayoutX(participantes[l].getX() + 80);
                        cardsBack[(j + 1)].setLayoutY(participantes[l].getY() + 80);
                        break;
                    case 6:
                        cardsBack[j].setLayoutX(participantes[l].getX() + 140);
                        cardsBack[j].setLayoutY(participantes[l].getY() + 20);
                        cardsBack[(j + 1)].setLayoutX(participantes[l].getX() + 180);
                        cardsBack[(j + 1)].setLayoutY(participantes[l].getY() + 20);
                        break;
                    default:
                        cardsBack[j].setLayoutX(participantes[l].getX() + 140);
                        cardsBack[j].setLayoutY(participantes[l].getY() - 20);
                        cardsBack[(j + 1)].setLayoutX(participantes[l].getX() + 180);
                        cardsBack[(j + 1)].setLayoutY(participantes[l].getY() - 20);
                        break;
                }
                l++;
            }
        });
    }

    @Override
    public void sendNotify(int posic) {
        if (posic == 0) {
            Platform.runLater(() -> {
                root.getChildren().remove(cartas[0]);
                root.getChildren().remove(cartas[1]);
            });

        } else {
            Platform.runLater(() -> {
                root.getChildren().remove(cardsBack[posic * 2 - 2]);
                root.getChildren().remove(cardsBack[posic * 2 - 1]);
            });
        }
    }
}
