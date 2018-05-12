package inf.unideb.hu.prk.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import org.pmw.tinylog.Logger;

public class FXMLController implements Initializable {

    @FXML private BorderPane BorderPane;
    private static Parent timer;
    private static Parent email;
    private static Parent view;

    @FXML void btnEmail(ActionEvent event) { BorderPane.setCenter(email);}
    @FXML void btnTimer(ActionEvent event) { BorderPane.setCenter(timer);}
    @FXML void btnView(ActionEvent event) { BorderPane.setCenter(view);}

    @FXML
    void btnExit(ActionEvent event){
        Platform.exit();
    }

        private static Parent loadFXML(String fxml){
        Parent root = null;
        try {
            root = FXMLLoader.load(FXMLController.class.getResource("/fxml/" + fxml + ".fxml"));
        }catch (IOException e){
            Logger.error(e);
        }
        return root;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        timer = loadFXML("timer");
        email = loadFXML("email");
        view = loadFXML("view");
    }
}
