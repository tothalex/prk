package inf.unideb.hu.prk.Controllers;

import inf.unideb.hu.prk.Database.DatabaseFactory;
import inf.unideb.hu.prk.Database.IDatabase;
import inf.unideb.hu.prk.Model.Time;
import inf.unideb.hu.prk.Timer.DBTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.pmw.tinylog.Logger;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class InsertController {

     @FXML
    private AnchorPane pane;

    @FXML
    private Button btnExit;

    @FXML
    private TextArea commentArea;

    @FXML
    private TextField startHfield;

    @FXML
    private TextField startMfield;

    @FXML
    private TextField endHfield;

    @FXML
    private TextField endMfield;

    @FXML
    private Label labelInvalid;

    @FXML
    void btnExit(ActionEvent event) {
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.close();
    }

    @FXML
    void btnInsert(ActionEvent event) {
        int Sh, Eh;
        int Sm, Em;
        labelInvalid.setVisible(false);
        try {
            Sh = Integer.parseInt(startHfield.getText());
            Sm = Integer.parseInt(startMfield.getText());
            Eh = Integer.parseInt(endHfield.getText());
            Em = Integer.parseInt(endMfield.getText());
            if (Sh > 23 || Sm > 59 || Eh > 23 || Em > 59) throw new Exception();
        }catch (Exception e){
            labelInvalid.setVisible(true);
            Logger.error(e);
            return;
        }

        LocalDateTime d = LocalDate.now().atTime(Sh, Sm);
        LocalDateTime d1 = LocalDate.now().atTime(Eh, Em);
        IDatabase database = DatabaseFactory.get();
        database.load();
        database.insertDBTime(new Time(d, d1, commentArea.getText()));
        database.save();

        btnExit.fire();
    }

}
