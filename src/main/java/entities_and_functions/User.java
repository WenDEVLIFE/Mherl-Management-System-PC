package entities_and_functions;

import javafx.beans.property.SimpleStringProperty;

public class User {
    public SimpleStringProperty id;

    public SimpleStringProperty username;

    public SimpleStringProperty role;

    public User(String id, String username, String role) {
        this.id = new SimpleStringProperty(id);
        this.username = new SimpleStringProperty(username);
        this.role = new SimpleStringProperty(role);
    }

    public String getId() {
        return id.get();
    }

    public void setId(String id) {
        this.id.set(id);
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

    public SimpleStringProperty idProperty() {
        return id;
    }

    public SimpleStringProperty usernameProperty() {
        return username;
    }

    public SimpleStringProperty roleProperty() {
        return role;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", username=" + username + ", role=" + role + '}';
    }
}
