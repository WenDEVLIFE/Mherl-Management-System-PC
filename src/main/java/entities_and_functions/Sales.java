package entities_and_functions;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;

public class Sales {
    public SimpleStringProperty productname;


    public SimpleIntegerProperty  productquantity;

    public SimpleIntegerProperty  totalprice;

    public SimpleStringProperty  date;


    public Sales(String productname, int productquantity, int totalprice, String date) {
        this.productname = new SimpleStringProperty(productname);
        this.productquantity = new SimpleIntegerProperty(productquantity);
        this.totalprice = new SimpleIntegerProperty(totalprice);
        this.date = new SimpleStringProperty(date);
    }

    public String getProductname() {
        return productname.get();
    }

    public int getProductquantity() {
        return productquantity.get();
    }

    public int getTotalprice() {
        return totalprice.get();
    }


    public String getDate() {
        return date.get();
    }

    public void setProductname(String productname) {
        this.productname.set(productname);
    }

    public void setProductquantity(int productquantity) {
        this.productquantity.set(productquantity);
    }

    public void setTotalprice(int totalprice) {
        this.totalprice.set(totalprice);
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public SimpleStringProperty productnameProperty() {
        return productname;
    }

    public SimpleIntegerProperty productquantityProperty() {
        return productquantity;
    }

    public SimpleIntegerProperty totalpriceProperty() {
        return totalprice;
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public void setProductname(SimpleStringProperty productname) {
        this.productname = productname;
    }

    public void setProductquantity(SimpleIntegerProperty productquantity) {
        this.productquantity = productquantity;
    }

    public void setTotalprice(SimpleIntegerProperty totalprice) {
        this.totalprice = totalprice;
    }

    public void setDate(SimpleStringProperty date) {
        this.date = date;
    }

    public String toString() {
        return "Sales{" +
                "productname=" + productname +
                ", productquantity=" + productquantity +
                ", totalprice=" + totalprice +
                ", date=" + date +
                '}';
    }


    public SimpleStringProperty productNameProperty() {
        return productname;
    }
}
