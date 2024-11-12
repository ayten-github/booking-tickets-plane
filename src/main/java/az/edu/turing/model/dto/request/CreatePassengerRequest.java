package az.edu.turing.model.dto.request;

public class CreatePassengerRequest {

    private final String firstName;
    private final String lastName;
    private final String login;
    private final String password;

//    public CreatePassengerRequest(String firstName, String login, String password) {
//        this(firstName, login, null, password);
//    }

    public CreatePassengerRequest(String firstName, String lastName, String login, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }
}
