package tables;




import entities_and_functions.Products;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.util.Callback;


public class ButtonCellDeleteProducts extends TableCell<Products, Void> {
    private final Button button;

    private TableView<Products> ProductTable;

    private ObservableList<Products> ProductList;



    // Constructor for the ButtonCell
    public ButtonCellDeleteProducts (String buttonText, TableView<Products> ProductTable, ObservableList<Products> ProductList) {
        this.button = new Button(buttonText);
        this.ProductTable = ProductTable;
        this.ProductList = ProductList;


        this.button.setOnAction(event -> {
            Products SelectedCar = getTableRow().getItem();
            if (SelectedCar  != null) {
                if (buttonText.equals("Delete Car")) {

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation Dialog");
                    alert.setHeaderText("Delete Car");
                    alert.setContentText("Are you sure you want to delete this car?");
                    alert.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {

                            // Delete car from database


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
            if ("Delete Car".equals(buttonText)) {
                button.getStyleClass().add("jfx-buttoncell");
            } else if ("Rent Car".equals(buttonText)) {
                button.getStyleClass().add("jfx-buttoncell");
            }

            setGraphic(button);
        }
    }


    // Static method to create a callback for the table column
    public static Callback<TableColumn<Products, Void>, TableCell<Products, Void>> forTableColumn(String buttonText, TableView<Products> ProductTable, ObservableList<Products> ProductList) {
        return param -> new ButtonCellDeleteProducts (buttonText , ProductTable, ProductList);
    }
}

