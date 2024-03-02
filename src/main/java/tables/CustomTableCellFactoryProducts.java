package tables;


import entities_and_functions.Products;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

// This class is used to create custom table cells
public class CustomTableCellFactoryProducts {
    public static Callback<TableColumn<Products, String>, TableCell<Products, String>> cellFactoryForString() {
        return column -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item);
                setAlignment(Pos.CENTER);
            }
        };
    }

    public static Callback<TableColumn<Products, Integer>, TableCell<Products, Integer>> cellFactoryForInteger() {
        return column -> new TableCell<>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item.toString());
                setAlignment(Pos.CENTER);
            }
        };
    }

    public static Callback<TableColumn<Products, String>, TableCell<Products, String>> createCenteredStringCell() {
        return column -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item);
                setAlignment(Pos.CENTER);
            }
        };
    }
}