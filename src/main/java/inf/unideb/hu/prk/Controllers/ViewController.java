package inf.unideb.hu.prk.Controllers;

import inf.unideb.hu.prk.Database.DatabaseFactory;
import inf.unideb.hu.prk.Database.IDatabase;
import inf.unideb.hu.prk.Model.TimeRecursive;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.pmw.tinylog.Logger;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class ViewController {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    private TableView<TimeRecursive> table;

    @FXML
    private TextField textfieldTotal;

    @FXML
    void btnInsert(ActionEvent event) {
         try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/fxml/insert.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 400, 200);
            Stage stage = new Stage();
            stage.setTitle("Insert");
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });
            stage.show();
        } catch (IOException e) {
            Logger.info(e);
            return;
        }
    }

    @FXML
    void btnRefresh(ActionEvent event) {
        TableColumn<TimeRecursive, String> colStart = new TableColumn<>("Start");
        colStart.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getStart().format(formatter)));
        TableColumn<TimeRecursive, String> colEnd = new TableColumn<>("End");
        colEnd.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getEnd().format(formatter)));
        TableColumn<TimeRecursive, String> colCom = new TableColumn<>("Comments");
        colCom.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getComment()));
        TableColumn<TimeRecursive, String> colDur = new TableColumn<>("Duration");
        colDur.setCellValueFactory(param -> new ReadOnlyStringWrapper(DurationFormatUtils.formatDuration(param.getValue().getDuration(), "HH:mm")));

        colStart.setPrefWidth(150);
        colEnd.setPrefWidth(150);
        colDur.setPrefWidth(80);
        colCom.setPrefWidth(165);

        ObservableList<TimeRecursive> list = FXCollections.observableArrayList();
        IDatabase database = DatabaseFactory.get();
        database.load();

        if (database.getDatabaseListCurrentMonth().isEmpty()) return;
        database.getDatabaseListCurrentMonth().forEach(x -> list.add(new TimeRecursive(x)));

        table.getColumns().setAll(colStart, colEnd, colDur, colCom);
        table.getItems().clear();
        table.getItems().addAll(list);
        textfieldTotal.setText(database.totalCurrentMonth());

    }
}
