package entities_and_functions;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Products {

    public SimpleStringProperty productName;

    public SimpleIntegerProperty productPrice;

    public SimpleIntegerProperty productQuantity;

    public Products(String productName, int productPrice, int productQuantity) {
        this.productName = new SimpleStringProperty(productName);
        this.productPrice = new SimpleIntegerProperty(productPrice);
        this.productQuantity = new SimpleIntegerProperty(productQuantity);
    }

    public String getProductName() {
        return productName.get();
    }

    public void setProductName(String productName) {
        this.productName.set(productName);
    }

    public int getProductPrice() {
        return productPrice.get();
    }

    public void setProductPrice(int productPrice) {
        this.productPrice.set(productPrice);
    }

    public int getProductQuantity() {
        return productQuantity.get();
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity.set(productQuantity);
    }

    public SimpleStringProperty productNameProperty() {
        return productName;
    }

    public SimpleIntegerProperty productPriceProperty() {
        return productPrice;
    }

    public SimpleIntegerProperty productQuantityProperty() {
        return productQuantity;
    }

    public String toString() {
        return "Product Name: " + productName.get() + " Product Price: " + productPrice.get() + " Product Quantity: " + productQuantity.get();
    }
    
}
