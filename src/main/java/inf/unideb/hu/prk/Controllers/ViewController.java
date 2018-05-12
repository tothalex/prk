package inf.unideb.hu.prk.Controllers;

import inf.unideb.hu.prk.Database.DatabaseFactory;
import inf.unideb.hu.prk.Database.IDatabase;
import inf.unideb.hu.prk.Model.TimeRecursive;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.apache.commons.lang3.time.DurationFormatUtils;

import java.time.format.DateTimeFormatter;

public class ViewController {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @FXML
    private TableView<TimeRecursive> table;

    @FXML
    private TextField textfieldTotal;

    @FXML
    void btnInsert(ActionEvent event) {

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
