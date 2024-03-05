package tables;




import com.example.mherlmanagementsystem.UserController;
import entities_and_functions.User;
import firebase.FirebaseController;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.util.Callback;


public class ButtonCellDeleteUser extends TableCell<User, Void> {
    private final Button button;

    private TableView<User> UserTable;

    private ObservableList<User> userList;

    private final String username;



    // Constructor for the ButtonCell
    public ButtonCellDeleteUser (String buttonText, TableView<User> UserTable, ObservableList<User> userList, String username) {
        this.button = new Button(buttonText);
        this.UserTable = UserTable;
        this.userList = userList;
        this.username = username;


        this.button.setOnAction(event -> {
            User userselected = getTableRow().getItem();
            if (userselected  != null) {
                if (buttonText.equals("Delete User")) {

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation Dialog");
                    alert.setHeaderText("DeleteDelete Product");
                    alert.setContentText("Are you sure you want to delete this product?");
                    alert.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {

                            // Delete car from database

                            Platform.runLater(() -> {

                                FirebaseController.getInstance().deleteUser(userselected);
                                userList.remove(userselected);
                                UserTable.refresh();


                            });


                        }
                    });




                }
                // Rent Car function
                else if (buttonText.equals("Show info")) {

                    System.out.println("Rent Car");

                    FirebaseController.getInstance().retrieveUser(userselected);




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
            if ("Delete User".equals(buttonText)) {
                button.getStyleClass().add("buttons");
            } else if ("Show info".equals(buttonText)) {
                button.getStyleClass().add("buttons");
            }

            setGraphic(button);
        }
    }


    // Static method to create a callback for the table column
    public static Callback<TableColumn<User, Void>, TableCell<User, Void>> forTableColumn(String buttonText, TableView<User> UserTable, ObservableList<User> userList, String username) {
        return param -> new ButtonCellDeleteUser (buttonText , UserTable, userList, username);
    }
}
