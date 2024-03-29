package tables;




import com.example.mherlmanagementsystem.ProductController;
import entities_and_functions.Products;
import firebase.FirebaseController;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.util.Callback;


public class ButtonCellDeleteProducts extends TableCell<Products, Void> {
    private final Button button;

    private TableView<Products> ProductTable;

    private ObservableList<Products> ProductList;

    private final String username;



    // Constructor for the ButtonCell
    public ButtonCellDeleteProducts (String buttonText, TableView<Products> ProductTable, ObservableList<Products> ProductList, String username) {
        this.button = new Button(buttonText);
        this.ProductTable = ProductTable;
        this.ProductList = ProductList;
        this.username = username;


        this.button.setOnAction(event -> {
            Products Selectedproducts = getTableRow().getItem();
            if (Selectedproducts  != null) {
                if (buttonText.equals("Delete Product")) {

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation Dialog");
                    alert.setHeaderText("DeleteDelete Product");
                    alert.setContentText("Are you sure you want to delete this product?");
                    alert.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {

                            // Delete car from database

                            Platform.runLater(() -> {
                                FirebaseController.getInstance().deleteProduct(Selectedproducts, username);
                                ProductList.remove(Selectedproducts);
                                ProductTable.refresh();
                            });


                        }
                    });




                }
                // Rent Car function
                else if (buttonText.equals("Buy a product")) {

                    Platform.runLater(() -> {
                        System.out.println("Buy a product");

                        // Go to buy product and get the controller
                        ProductController buy = ProductController.getController_products();
                        buy.GoToBuyProduct(Selectedproducts);

                    });



                }

                else if (buttonText.equals("Product Info")) {

                  Platform.runLater(() -> {
                      System.out.println("Product Info");

                      // Go to edit product and get the controller
                      ProductController edit = ProductController.getController_products();
                      edit.GoToEditProduct(Selectedproducts);
                  });
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
            if ("Delete Product".equals(buttonText)) {
                button.getStyleClass().add("buttons");
            } else if ("Buy a product".equals(buttonText)) {
                button.getStyleClass().add("buttons");
            }
            else if ("Product Info".equals(buttonText)) {
                button.getStyleClass().add("buttons");
            }

            setGraphic(button);
        }
    }


    // Static method to create a callback for the table column
    public static Callback<TableColumn<Products, Void>, TableCell<Products, Void>> forTableColumn(String buttonText, TableView<Products> ProductTable, ObservableList<Products> ProductList, String username) {
        return param -> new ButtonCellDeleteProducts (buttonText , ProductTable, ProductList, username);
    }
}

