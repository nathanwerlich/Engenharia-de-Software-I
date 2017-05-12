package javapoker.view;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MainScreen extends Application {

    @Override
    public void start(Stage loggedStage) {
        Button novoG = new Button("Novo Jogo");
        Button logout = new Button("Logout");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        grid.add(novoG, 0, 0);
        grid.add(logout, 0, 2);
        Scene cena = new Scene(grid, 600, 450);
        loggedStage.setTitle("Poker");
        loggedStage.setScene(cena);
        loggedStage.show();
        
       novoG.setOnAction(new EventHandler<ActionEvent>() {
           public void handle(ActionEvent event){
               GameStartOptions gS = new GameStartOptions();
               gS.start(loggedStage);
           }
       });
    }

}
