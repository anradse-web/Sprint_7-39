package pojo;


public class CourierModel {

    private  String login;
    private  String password;
    private  String firstName;

    public CourierModel(String courierLogin, String courierPassword, String courierFirstName) {
        this.login = courierLogin;
        this.password = courierPassword;
        this.firstName = courierFirstName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
