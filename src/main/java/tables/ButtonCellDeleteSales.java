package tables;




import entities_and_functions.Products;
import entities_and_functions.Sales;
import firebase.FirebaseController;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.util.Callback;


public class ButtonCellDeleteSales extends TableCell<Sales, Void> {
    private final Button button;

    private TableView<Sales> SalesTable;

    private ObservableList<Sales> Saleslist;

    private final String username;



    // Constructor for the ButtonCell
    public ButtonCellDeleteSales (String buttonText, TableView<Sales> SalesTable, ObservableList<Sales> Saleslist, String username) {
        this.button = new Button(buttonText);
        this.SalesTable = SalesTable;
        this.Saleslist = Saleslist;
        this.username = username;


        this.button.setOnAction(event -> {
            Sales Selectedproducts = getTableRow().getItem();
            if (Selectedproducts  != null) {
                if (buttonText.equals("Delete Sales")) {

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation Dialog");
                    alert.setHeaderText("DeleteDelete Product");
                    alert.setContentText("Are you sure you want to delete this product?");
                    alert.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {

                            // Delete car from database

                            Platform.runLater(() -> {
                                FirebaseController.getInstance().deleteSales(Selectedproducts, username);
                                SalesTable.getItems().remove(Selectedproducts);
                                Saleslist.remove(Selectedproducts);

                            });


                        }
                    });




                }
                // Rent Car function
                else if (buttonText.equals("Rent Car")) {

                    System.out.println("Rent Car");


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
            if ("Delete Sales".equals(buttonText)) {
                button.getStyleClass().add("buttons");
            } else if ("Rent Car".equals(buttonText)) {
                button.getStyleClass().add("buttons");
            }

            setGraphic(button);
        }
    }


    // Static method to create a callback for the table column
    public static Callback<TableColumn<Sales, Void>, TableCell<Sales, Void>> forTableColumn(String buttonText, TableView<Sales> SalesTable, ObservableList<Sales> Saleslist, String username) {
        return param -> new ButtonCellDeleteSales (buttonText , SalesTable, Saleslist, username);
    }
}

