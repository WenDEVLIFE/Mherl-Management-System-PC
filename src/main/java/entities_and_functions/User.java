package entities_and_functions;

import javafx.beans.property.SimpleStringProperty;

public class User {


    public SimpleStringProperty username;

    public SimpleStringProperty role;

    public User( String username, String role) {

        this.username = new SimpleStringProperty(username);
        this.role = new SimpleStringProperty(role);
    }




    public String getUsername() {
        return username.get();
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getRole() {
        return role.get();
    }

    public void setRole(String role) {
        this.role.set(role);
    }



    public SimpleStringProperty usernameProperty() {
        return username;
    }

    public SimpleStringProperty roleProperty() {
        return role;
    }

    @Override
    public String toString() {

        return "User{" +
                "username=" + username +
                ", role=" + role +
                '}';
    }
}
