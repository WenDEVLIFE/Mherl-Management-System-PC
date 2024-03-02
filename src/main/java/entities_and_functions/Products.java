package entities_and_functions;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Products {

    public SimpleStringProperty productName;

    public SimpleStringProperty  productPrice;

    public SimpleIntegerProperty productQuantity;

    public Products(String productName, String productPrice, int productQuantity) {
        this.productName = new SimpleStringProperty(productName);
        this.productPrice = new SimpleStringProperty (productPrice);
        this.productQuantity = new SimpleIntegerProperty(productQuantity);
    }

    public String getProductName() {
        return productName.get();
    }

    public String getProductPrice() {
        return productPrice.get();
    }

    public int getProductQuantity() {
        return productQuantity.get();
    }

    public void setProductName(String productName) {
        this.productName.set(productName);
    }


    public void setProductQuantity(int productQuantity) {
        this.productQuantity.set(productQuantity);
    }

    public SimpleStringProperty productNameProperty() {
        return productName;
    }

    public SimpleStringProperty productPriceProperty() {
        return productPrice;
    }


    public SimpleIntegerProperty productQuantityProperty() {
        return productQuantity;
    }

    public void setProductQuantity(SimpleIntegerProperty productQuantity) {
        this.productQuantity = productQuantity;
    }

    public void setProductPrice(SimpleStringProperty productPrice) {
        this.productPrice = productPrice;
    }

    public void setProductName(SimpleStringProperty productName) {
        this.productName = productName;
    }




    public void setProductPrice(int productPrice) {
        this.productPrice.set(String.valueOf(productPrice));
    }



    public void setProductPrice(String productPrice) {
        this.productPrice.set(productPrice);
    }


    public void setProductQuantity(String productQuantity) {
        this.productQuantity.set(Integer.parseInt(productQuantity));
    }

    public void setProductName(int productName) {
        this.productName.set(String.valueOf(productName));
    }

   public String toString() {
        return "Product Name: " + productName + " Product Price: " + productPrice + " Product Quantity: " + productQuantity;
    }





}
