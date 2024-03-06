package entities_and_functions;

import javafx.beans.property.SimpleStringProperty;

public class Report {

    public SimpleStringProperty username;

    public SimpleStringProperty activity;

    public SimpleStringProperty date;

    public SimpleStringProperty time;


    public Report (String username, String activity, String date, String time) {
        this.username = new SimpleStringProperty(username);
        this.activity = new SimpleStringProperty(activity);
        this.date = new SimpleStringProperty(date);
        this.time = new SimpleStringProperty(time);
    }

    public String getUsername() {
        return username.get();
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getActivity() {
        return activity.get();
    }

    public void setActivity(String activity) {
        this.activity.set(activity);
    }

    public String getDate() {
        return date.get();
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getTime() {
        return time.get();
    }

    public void setTime(String time) {
        this.time.set(time);
    }

    public SimpleStringProperty usernameProperty() {
        return username;
    }

    public SimpleStringProperty activityProperty() {
        return activity;
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public SimpleStringProperty timeProperty() {
        return time;
    }

    @Override
    public String toString() {
        return "Report{" + "username=" + username + ", activity=" + activity + ", date=" + date + ", time=" + time + '}';
    }
}
