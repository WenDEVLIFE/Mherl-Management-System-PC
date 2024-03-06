package tables;





import com.example.mherlmanagementsystem.UserController;
import entities_and_functions.Report;
import entities_and_functions.User;
import firebase.FirebaseController;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.util.Callback;


public class ButtonCellDeleteReport extends TableCell<Report, Void> {
    private final Button button;

    private TableView<Report> ReportTable;

    private ObservableList<Report> reportList;

    private final String username;



    // Constructor for the ButtonCell
    public ButtonCellDeleteReport (String buttonText, TableView<Report> ReportTable, ObservableList<Report> reportList, String username) {
        this.button = new Button(buttonText);
        this.ReportTable = ReportTable;
        this.reportList = reportList;
        this.username = username;


        this.button.setOnAction(event -> {
            Report selected = getTableRow().getItem();
            if (selected  != null) {
                if (buttonText.equals("Delete Report")) {

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation Dialog");
                    alert.setHeaderText("DeleteDelete Product");
                    alert.setContentText("Are you sure you want to delete this product?");
                    alert.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {

                            // Delete car from database

                            Platform.runLater(() -> {




                            });


                        }
                    });




                }
                // Rent Car function
                else if (buttonText.equals("Show info")) {






                }
            }
        });


    }




    @Override
    protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setGraphic(null);
        } else {
            // Set style class for the button based on buttonText
            String buttonText = button.getText();
            if ("Delete Report".equals(buttonText)) {
                button.getStyleClass().add("buttons");
            } else if ("Show info".equals(buttonText)) {
                button.getStyleClass().add("buttons");
            }

            setGraphic(button);
        }
    }


    // Static method to create a callback for the table column
    public static Callback<TableColumn<Report, Void>, TableCell<Report, Void>> forTableColumn(String buttonText, TableView<Report> ReportTable, ObservableList<Report> reportList, String username) {
        return param -> new ButtonCellDeleteReport (buttonText , ReportTable, reportList, username);
    }
}

