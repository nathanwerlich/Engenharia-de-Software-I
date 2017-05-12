package javapoker.view;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javapoker.controle.Main;

public class TableUI extends Application {

    private int numeroDeParticipantes;
    private Group root;
    private HBox opcoes = new HBox();
    private ImageView[] participants;

    public TableUI(int _numeroDeParticipantes, Stage stage) {
        numeroDeParticipantes = _numeroDeParticipantes;
        start(stage);
    }

    @Override
    public void start(Stage primaryStage) {

        Scene cena = sentarJogadores();

        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        primaryStage.setMaxWidth(800);
        primaryStage.setMaxHeight(600);
        primaryStage.setScene(cena);
        primaryStage.sizeToScene();
        primaryStage.show();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
    }

    public Scene sentarJogadores() {
        Image imgMesa = new Image("javapoker/imgs/table_burned.png");

        Image participante = new Image("javapoker/imgs/jogador");
        participants = new ImageView[numeroDeParticipantes];

        HBox caixamesa = new HBox();
        ImageView mesaView = new ImageView();
        mesaView.setImage(imgMesa);

        root = new Group();
        for (int i = 0; i < participants.length; i++) {
            participants[i] = new ImageView();
            participants[i].setImage(participante);
            root.getChildren().add(participants[i]);
        }
        participants[0].setX(300);
        participants[0].setY(410);
        for (int i = 1; i < participants.length; i++) {
            switch (i) {
                case 1:
                    participants[i].setX(participants[i - 1].getX() + 330);
                    participants[i].setY(participants[i - 1].getY() - 70);
                    break;
                case 2:
                    participants[i].setX(participants[i - 1].getX());
                    participants[i].setY(participants[i - 1].getY() - 270);
                    break;
                case 3:
                    participants[i].setX(participants[i - 3].getX());
                    participants[i].setY(participants[i - 1].getY() - 50);
                    break;
                case 4:
                    participants[i].setX(participants[i - 1].getX() - 330);
                    participants[i].setY(participants[i - 2].getY());
                    break;
                default:
                    participants[i].setX(participants[i - 1].getX());
                    participants[i].setY(participants[i - 1].getY() + 270);
                    break;
            }
        }
        caixamesa.getChildren().add(mesaView);
        root.getChildren().add(caixamesa);
        caixamesa.setLayoutX(30);
        caixamesa.setLayoutY(100);
        Scene cena = new Scene(root);
        cena.setFill(Color.BLACK);
        return cena;
    }

    public void setPlayerOptions() {
        Button pagar = new Button("Pagar");
        Button aumentar = new Button("Aumentar");
        Button desistir = new Button("Desistir");
        Button passar = new Button("Passar");

        TextField valor = new TextField("25");

        opcoes.getChildren().add(pagar);
        opcoes.getChildren().add(aumentar);
        opcoes.getChildren().add(desistir);
        opcoes.getChildren().add(passar);
        opcoes.getChildren().add(valor);
        root.getChildren().add(opcoes);

        valor.setMaxSize(opcoes.getWidth() + 40, 10);

        valor.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
             if (!newValue.matches("\\d*")) {
                valor.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        opcoes.setLayoutX(20);
        opcoes.setLayoutY(540);
        opcoes.setVisible(false);

        desistir.setOnAction((ActionEvent e) -> {
            Main.gCont.ExecutaJogada(0);
        });
        pagar.setOnAction((ActionEvent e) -> {
            Main.gCont.ExecutaJogada(1);
        });
        aumentar.setOnAction((ActionEvent e) -> {
            Main.gCont.setRaiseAmt(Integer.parseInt(valor.getText()));
            Main.gCont.ExecutaJogada(2);
        });
        passar.setOnAction(pagar.getOnAction());
    }

    public void mostraOpc(boolean coloca) {
        if (coloca) {
            opcoes.setVisible(coloca);
        } else {
            opcoes.setVisible(coloca);
        }
    }

    public ImageView[] getParticipants() {
        return participants;
    }

    public Group getVisual() {
        return root;
    }
}
