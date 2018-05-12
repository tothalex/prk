package inf.unideb.hu.prk.Controllers;

import inf.unideb.hu.prk.Timer.DBTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.pmw.tinylog.Logger;
import inf.unideb.hu.prk.Database.*;

import java.util.Timer;
import java.util.TimerTask;

public class TimerController {

    private IDatabase database = null;
    private DBTimer dbTimer = new DBTimer();
    private boolean STOP = false;
    private Timer timer = new Timer();

    @FXML
    private TextArea textareaComment;

    @FXML
    private Label labelStartPressed;

    @FXML
    private Label labelStartnPressed;

    @FXML
    private Label labelrun;

    @FXML
    private TextField textfieldStopWatch;

    @FXML
    void btnComment(ActionEvent event) {
         try {
            Logger.info("Comment button pressed!");
            String comment = textareaComment.getText();
            dbTimer.addComment(comment);
            Logger.info("Comment added!(" + textareaComment.getText() + ")");
            textareaComment.setText("");
        } catch (Exception e) {
            Logger.error(e);
            return;
        }
    }

    @FXML
    void btnEnd(ActionEvent event) {
         try {
            labelStartPressed.setVisible(false);
            labelrun.setVisible(false);
            dbTimer.stop();
            if (database == null) database = DatabaseFactory.get();
            database.load();
            database.insertDBTime(dbTimer.getTime());
            database.save();
            dbTimer.reset();
            STOP = true;

            Logger.info("Stop button pressed!");
        } catch (Exception e) {
            Logger.error(e.getMessage());
            labelStartnPressed.setVisible(true);
            return;
        }
    }

    @FXML
    void btnStart(ActionEvent event) {
         try {
            labelStartnPressed.setVisible(false);
            labelrun.setVisible(true);
            dbTimer.start();
            Logger.info("Start button pressed!");
            STOP = false;
            textfieldStopWatch.setText("00h:00m");
            timer.scheduleAtFixedRate(new TimerTask() {
                int h = 0;
                int m = 0;

                @Override
                public void run() {
                    if (STOP) cancel();
                    m++;
                    if (m == 60) {
                        m = 0;
                    }
                    Logger.info("[" + h + ":" + m + "]");
                    textfieldStopWatch.setText(
                            (h <= 9 ? "0" + h : (Integer.toString(h))) + "h:" +
                                    (m <= 9 ? "0" + m : (Integer.toString(m))) + "m");
                }
            }, 60 * 1000, 60 * 1000);
        } catch (Exception e) {
            Logger.error(e.getMessage());
            labelStartPressed.setVisible(true);
            labelrun.setVisible(false);
            return;
        }
    }

}
