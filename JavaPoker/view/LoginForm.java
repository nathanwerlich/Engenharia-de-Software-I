
package javapoker.view;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javapoker.persistencia.DatabaseAccess;

public class LoginForm extends Application{
    
    @Override
    public void start(Stage primaryStage) {
        
        //for debuggin purposes grid.setGridLinesVisible(true);
        final Text actiontarget = new Text();
        
        HBox loginBox = new HBox(10);
        Button login = new Button();
        
        Button cancel = new Button();
        
        HBox createAccBox = new HBox();
        Button criarConta = new Button();
        
        Text loginTxt = new Text("Logar");
        Label usrLbl = new Label("Usuário: ");
        Label pwLbl = new Label("Senha: ");
        
        TextField userField = new TextField();
        PasswordField pwField = new PasswordField();
        userField.setMinWidth(250);
        pwField.setMinWidth(250);
        
        loginTxt.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
        login.setText("Entrar");
        cancel.setText("Cancelar");
        criarConta.setText("Criar Conta");
        
        //Sintaxe : grid.add(componente,coluna,linha)
        //Painel grid, alinhamento no centro, "gap" vertical e horizontal = 10
        //Padding do grid 25 pra todos lados.
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.add(loginTxt,0,0,2,1);
        grid.add(usrLbl,0,1);
        grid.add(pwLbl,0,2);
        grid.add(userField,1,1);
        grid.add(pwField,1,2);
        
        grid.add(cancel,0,3);
        
        //usando a hbox pra deixar o botão de logar na posição legal.
        loginBox.setAlignment(Pos.BOTTOM_RIGHT);
        loginBox.getChildren().add(login);
        grid.add(loginBox,1,3);
        
        createAccBox.setAlignment(Pos.BOTTOM_LEFT);
        createAccBox.getChildren().add(criarConta);
        grid.add(createAccBox,0,4);
        
        //um text pra dar umas mensagens no panel e pa.
        grid.add(actiontarget, 1, 4);

        //Instancia a cena, com o panel e o tamanho.
        Scene scene = new Scene(grid, 600, 450);
        
        primaryStage.setTitle("POKER");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        
        
        // ações dos botões. 
        login.setOnAction((ActionEvent event) -> {
            //Aqui vai chamar a tela principal que vai dar a opção ao jogador
            //de começar um novo jogo.
            boolean contaExiste = false;
            String login1 = userField.getText();
            String senha = pwField.getText();
            DatabaseAccess db = new DatabaseAccess();
            try {
                db.connect("org.postgresql.Driver", "jdbc:postgresql://localhost:5432/PokerSim", "postgres", "admin");
                contaExiste = db.check(login1, senha);
                db.close();
            }catch (Exception e) {}
            if(contaExiste) {
                MainScreen telaP = new MainScreen();
                telaP.start(primaryStage);
            } else {
                actiontarget.setText("Usuário e/ou senha Incorreto(s).");
            }
        });
        criarConta.setOnAction((ActionEvent event) -> {
            NewContaForm contaNova = new NewContaForm();
            contaNova.start(primaryStage);
        });
        cancel.setOnAction((ActionEvent event) -> {
            // Se o usuário apertou o botão de cancela ele quita do game.
            System.exit(0);
        });
    }
}