/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javapoker.view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author gustavob
 */
public class NewContaForm extends Application {

    @Override
    public void start(Stage newContaStage) {
        Text createAcc = new Text("Criar Nova Conta");
        Button criar = new Button("Criar");
        Button cancelar = new Button("Cancelar");
        //Campos de text e senha
        TextField usuarioF = new TextField();
        PasswordField senhaF = new PasswordField();
        TextField emailF = new TextField();
        TextField recoverF = new TextField();
        //Labels para os campos de texto e senha.
        Label userLbl = new Label("Usuário: ");
        Label pwLbl = new Label("Senha: ");
        Label emailLbl = new Label("Email: ");
        Label recoverLbl = new Label("Palavra de Recuperação: ");
        
        createAcc.setFont(Font.font("Tahoma", FontWeight.NORMAL, 40));
        
         GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        //colocando o "cabeçario" no grid.
        grid.add(createAcc,0,0,2,1);
        //colocando os labels no grid um em baixo do outro
        grid.add(userLbl,0,1);
        grid.add(pwLbl,0,2);
        grid.add(emailLbl,0,3);
        grid.add(recoverLbl,0,4);
        
        grid.add(usuarioF,1,1);
        grid.add(senhaF,1,2);
        grid.add(emailF,1,3);
        grid.add(recoverF,1,4);
        
        grid.add(criar,1,5);
        grid.add(cancelar,0,5);
        
        Scene scene = new Scene(grid, 600, 450);
        
        newContaStage.setTitle("POKER");
        newContaStage.setScene(scene);
        newContaStage.show();
        
        
    }
    
}
