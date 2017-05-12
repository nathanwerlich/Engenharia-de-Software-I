package javapoker.view;

import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.control.Label;

//cronometro pra mostrar pro player quanto tempo ele tem pra fazer as ações dele.
//tem que arrumar o timertask certinho dele, e ver como eu vou fazer pra arrumar ele de acordo com o controller do game
public class Cronometro {

    private int cronometro;
    private boolean timer;
    private TimerTask tt;
    private Timer t = new Timer("Metronome", true);

    public Cronometro() {
        cronometro = 30;
        timer = false;
    }

    public void setCronometro(Group root) {
        Label relogio = new Label("" + cronometro);
        relogio.setScaleX(2);
        relogio.setScaleY(2);
        relogio.setLayoutX(50);
        relogio.setLayoutY(500);
        timer = true;
        Platform.runLater(() -> {
            root.getChildren().add(relogio);
            relogio.setVisible(false);
        });
        tt = new TimerTask() {

            @Override
            public void run() {
                if (!timer) {
                    try {
                        relogio.setVisible(false);
                        tt.cancel();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    updateTime();
                    Platform.runLater(() -> {
                        relogio.setVisible(true);
                        relogio.setText("" + cronometro);
                    });
                }
            }
        };
        t.scheduleAtFixedRate(tt, 10, 1000);
    }

    public synchronized void stopTimer() {
        timer = false;
        cronometro = 30;
    }

    public void updateTime() {
        cronometro--;
    }

    public int getCronometro() {
        return cronometro;
    }
}
