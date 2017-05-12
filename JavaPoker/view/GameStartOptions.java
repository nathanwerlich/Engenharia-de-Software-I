package javapoker.view;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javapoker.controle.GameController;
import javapoker.controle.Main;

public class GameStartOptions extends Application{
    
    @Override
    public void start(Stage tableState) {
        Label numeroDeBots = new Label("Número de bots");
        Label quantidadeDeFichas = new Label("Quantidade de fichas iniciais para cada Jogador.");
        Button comecar = new Button("Iniciar");
        String[] botsN = {"1", "2", "3", "4", "5"};
        String[] fichasN = {"100", "500", "1000", "5000", "10000"};
        ComboBox bots = new ComboBox<>();
        ComboBox fichas = new ComboBox<>();
        
        bots.getItems().addAll((Object[]) botsN);
        fichas.getItems().addAll((Object[]) fichasN);
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.add(numeroDeBots,0,0);
        grid.add(quantidadeDeFichas,0,1);
        grid.add(bots,1,0);
        grid.add(fichas,1,1);
        grid.add(comecar,2,1);
        
        Scene cena = new Scene(grid, 600, 450);
        tableState.setTitle("New Game");
        tableState.setScene(cena);
        tableState.show();
        Main.gCont = new GameController(tableState);
        bots.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                Main.gCont.setNumeroDeBots((String) newValue);
            }
        });
        fichas.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                Main.gCont.setNumeroDeFichas((String) newValue);
            }
        });
        comecar.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                Main.gCont.begin();
            }
            
        });
        bots.getSelectionModel().select(0);
        fichas.getSelectionModel().select(0);
    }
}
