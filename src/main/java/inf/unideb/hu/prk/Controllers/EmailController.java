package inf.unideb.hu.prk.Controllers;

import inf.unideb.hu.prk.Database.DatabaseFactory;
import inf.unideb.hu.prk.Database.IDatabase;
import inf.unideb.hu.prk.Sender.Email;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.pmw.tinylog.Logger;

public class EmailController {

    @FXML private TextField textfieldFrom;
    @FXML private PasswordField passfieldGMAIL;
    @FXML private TextField textfieldTo;
    @FXML private Button btnSend;
    @FXML private Label labelSent;
    @FXML private Label labelFailed;

    @FXML
    void btnSend(ActionEvent event) {
        try {
            IDatabase database = DatabaseFactory.get();
            database.load();
            String content = database.formatCSV();
            if (content.length() == 0) throw new Exception("Empty content");
            Email email = new Email(textfieldFrom.getText(), passfieldGMAIL.getText(), textfieldTo.getText());
            email.send(content);
            labelFailed.setVisible(false);
            labelSent.setVisible(true);
        }catch (Exception ex){
            Logger.error(ex);
            labelSent.setVisible(false);
            labelFailed.setVisible(true);
            return;
        }
    }


}
