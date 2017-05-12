
package javapoker.controle;

import javafx.application.Application;
import javafx.stage.Stage;
import javapoker.view.LoginForm;

public class Main extends Application{
        public static GameController gCont;
    public static void main(String[] args) {
        
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        LoginForm lF = new LoginForm();
        lF.start(primaryStage);
    }
    
}
